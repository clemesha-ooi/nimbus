#!/bin/bash

BASEDIR_REL="`dirname $0`"
BASEDIR=`cd $BASEDIR_REL; pwd`

ENVIRONMENT_FILE="$BASEDIR/environment"
if [ ! -f "$ENVIRONMENT_FILE" ]; then
    echo "Problem, cannot find \"$ENVIRONMENT_FILE\""
    exit 1
fi

. $ENVIRONMENT_FILE

if [ "X$NIMBUS_NETDERBY_START" = "X" ] ; then
    echo "Problem, unexpected problem with environment, no NIMBUS_NETDERBY_START ?"
    exit 1
fi

if [ ! -d "$DERBY_DIR" ]; then
    echo "Can not find DERBY_DIR: $DERBY_DIR"
    exit 1
fi

NIMBUS_NETDERBY_START="$NIMBUS_NETDERBY_START $@"
(cd $DERBY_DIR; eval $NIMBUS_NETDERBY_START )

