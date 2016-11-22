#!/bin/bash

############# Compression
if (($DO_COMPRESS_KEEP_ORIGINAL)); then
	#Compress the database, keep the input file, force overwriting of pre-existing file
	echo '+ Compressing iters.db, keeping the original. If a pre-existing compressed file exists, overwrite and recreate it.'
	bzip2 -z --keep --force iters.db iters.db.bz2
	bzip2 -z --keep --force ./snapshots/iters.db ./snapshots/iters.db.bz2
fi
if (($DO_COMPRESS_REMOVE_ORIGINAL)); then
	#Compress the database, remove input file, force overwriting of pre-existing file
	echo '+ Compressing iters.db, remove original. If a pre-existing compressed file exists, overwrite and recreate it.'
	bzip2 -z --force iters.db iters.db.bz2
	bzip2 -z --force ./snapshots/iters.db ./snapshots/iters.db.bz2
fi
if (($DO_REMOVE_DB)); then
	#Remove the decompressed files
	rm iters.db
	rm ./snapshots/iters.db
fi

############# DeCompression
if (($DO_DECOMPRESS)); then
	#Decompress the SQL database, remove the original compressed file
	#bzip2 -d iters.db.bz2
	#bzip2 -d ./snapshots/iters.db.bz2

	#Decompress the SQL database without removing the original compressed file
	bzip2 -d --keep iters.db.bz2
	bzip2 -d --keep ./snapshots/iters.db.bz2
fi

echo 'Run '$PWD' --DONE'
echo $PWD >> $BASE/STATUS_itersdb
ls -lah iters.db iters.db.bz2 >> $BASE/STATUS_itersdb 2>> $BASE/STATUS_itersdb
