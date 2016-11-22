#!/usr/bin/env python
# $Id: gendb.py 2005 2009-07-31 14:14:48Z lsc $
# 
# Copyright (c) 2009 STFC Rutherford Appleton Laboratory 
# Author: Lee-Shawn Chin 
# Date  : July 2009
#
# NOTE: This is a proof-of-concept implementation. It was not designed for
#       production use but merely to explore what is possible
#       Use at your own risk ;)
#

import re
import os
import sys
from xml.dom import minidom
from xml.parsers.expat import ExpatError
from glob import glob
import sqlite3

outfile = "iters.db"

def error(mesg):
    print >>sys.stderr, ">>>>> (Error): %s" % mesg
    sys.exit(1)

def warn(mesg):
    print >>sys.stderr, ">> (Warning): %s" % mesg

def file_exist(fname):
    f = glob(fname)
    if fname in f: return True
    else: return False
    
def get_node_text(node):
    if len(node.childNodes) != 1 or node.childNodes[0].nodeType != node.TEXT_NODE:
        error("getText() routine used on non-text XML node")
    return node.childNodes[0].data.strip()

def get_agent_sql(data):
    # "data" should be in format
    # data["name"] = "agent_name"
    # data["iter"] = 1
    # data["vars"] = {}
    #     data["vars"]["varname"] = value
    global agents
    
    try:
        name = data["name"]
        vars = data["vars"]
        iter = data["iter"]
    except:
        error("BUG!! contact script author")
        
    # check agent exists
    if name not in agents.keys():
        error("Unknown agent found: %s" % name)
    
    # retrieve values in the right order
    keys = []
    values = []
    for v in vars.keys():
        if v == "name": continue
        if v not in agents[name]:
            error("Unknown agent memory : %s.%s" % (name, v))
        keys.append(v)
        values.append(vars[v])
    
    sql = []
    sql.append("insert into %s" % name)
    sql.append("(_ITERATION_NO,%s)" % ",".join(keys))
    sql.append("values (%d,'%s');" % (iter, "','".join(values)))    
    return " ".join(sql)

def load_iteration_file(itno, basedir, db):
    
    filename = os.path.join(basedir, "%d.xml"%itno)
    print "   + Reading file %s" % filename
    
    if not file_exist(filename):
        error("Strange! Input file disappeared : %s" % filename)
    try:
        file = open(filename, "r")
    except:
        error("Could not open file %s" % filename)
    
    
    db.execute("INSERT INTO _iters_(itno) VALUES (%d);" % i)
    
    data = {}
    in_xagent_tag = False
    line = file.readline()
    re_tag = re.compile(r"<(\w+)>([^<]*)</\1>")
    
    while line:
        line = line.strip()
        
        if not line: # ignore empty lines
             # get next line and restart loop
            line = file.readline()
            continue
        
        if in_xagent_tag:
            if line == "</xagent>": # end if this agent def
                in_xagent_tag = False
                db.execute( get_agent_sql(data) ) # store data in db
                del(data)
                
            else: # next agent mem
                match = re_tag.match(line)
                if not match: error("Unknown line: %s" % line)
                
                tag = match.group(1)
                val = match.group(2)
                
                if tag == "name":
                    data["name"] = val
                else:
                    data["vars"][tag] = val
                    
        else: # outside xagent tag
            if line == "<xagent>":
                in_xagent_tag = True
                data = {}
                data["iter"] = i
                data["vars"] = {}
        
        
        line = file.readline() # get next line
        
    file.close()

    
# get input arguments
if len(sys.argv) != 3:
    print >>sys.stderr, "Usage: %s <model.xml> <its_directory>" % sys.argv[0]
    sys.exit(0)
model  = sys.argv[1]
itsdir = sys.argv[2]

# check that model file exists
if not file_exist(model):
    error("Model file (%s) does not exist" % model)
    
# ensure directory exists
if not os.path.isdir(itsdir):
    error("Input directory (%s) does not exist" % itsdir)

# Retrieve list of data files
datafiles = glob(os.path.join(itsdir, "[0-9]*.xml"))
if len(datafiles) == 0:
    error("No data files found in %s" % itsdir)

# get list of iters from datafile names
print ""
print " - Scanning \"%s\"" % itsdir
iters = []
iter_pattern = "%s%s*([0-9]+)\.xml" % (itsdir, os.sep)
iter_re = re.compile(iter_pattern)
for df in datafiles:
    match = iter_re.match(df)
    if not match:
        error("BUG: Unknown line \nglob line = %s \npattern = %s" % (df, iter_pattern))
    iters.append(int(match.group(1)))
iters.sort()
print " - Found iters : %s" % str(iters)[1:-1]

# Initialise database
print " - Initialising database file : \"%s\"" % outfile
if file_exist(outfile):
    print "   ... File already exist. Deleting."
    try:
        os.remove(outfile)
    except:
        error("Could not delete file %s" % outfile)
db = sqlite3.connect(outfile, isolation_level=None) # manage our own transactions

# some performance tuning
db.execute("PRAGMA temp_store = MEMORY;")
db.execute("PRAGMA synchronous = OFF;")
db.execute("PRAGMA journal_mode = OFF;")

# parse xmml file to determine required database structure
print " - Analysing model"
agents = {}
models = [model]
while len(models) > 0:
    fname = models.pop()
    dirname = os.path.dirname(fname)
    
    # load xml file
    print "   + parsing %s" % fname
    try:
        dom = minidom.parse(fname)
    except IOError:
        error("Unable to read model file (%s)" % fname)
    except ExpatError:
        error("Invalid XML file (%s)" % fname)
        
    # detect nested models
    nodes = dom.getElementsByTagName("model")
    for node in nodes:
        status = get_node_text(node.getElementsByTagName("enabled")[0])
        if status != "true": continue # disabled. ignore
        
        # add nested model file to list of files
        modelfile = get_node_text(node.getElementsByTagName("file")[0])
        models.append(os.path.join(dirname, modelfile))
    del(nodes)
    
    # detect agents
    nodes = dom.getElementsByTagName("xagent")
    for node in nodes:
        # get agent name
        aname = get_node_text(node.getElementsByTagName("name")[0])
        varlist = []
        vars = node.getElementsByTagName("variable")
        # get list of agent memory vars
        for var in vars:
            varlist.append(get_node_text(var.getElementsByTagName("name")[0]))
        del(vars)
        
        # remove array brackets
        for i in xrange(len(varlist)):
            v = varlist[i]
            if "[" in v: varlist[i] = v[:v.index("[")]
            
        # add agent data to out list
        if aname in agents.keys(): # agent already exist. Merge
            for v in varlist: 
                if v not in agents[aname]: agents[aname].append(v)
        else: # new agent
            agents[aname] = varlist
    del(nodes)    
    
    # finished parsing this file 
    del(dom)

# Create db tables
print " - Initialising database structure"
print "   + creating table : _iters_"
db.execute("BEGIN;")
db.execute("CREATE TABLE _iters_ (itno)") # metadata table
for a in agents: # agent data tables
    sql = "CREATE TABLE %s (_ITERATION_NO,%s)" % (a, ",".join(agents[a]))
    print "   + creating table : %s" % a
    db.execute(sql)
db.execute("END;")
    
# Finally, load data from *.xml files
print " - Importing snapshot data into database"
for i in iters: 
    db.execute("BEGIN;")
    load_iteration_file(i, itsdir, db)
    db.execute("END;")

# Done!
print " - DONE. Output file : %s" % outfile
print ""

# close db
db.commit()
db.close()