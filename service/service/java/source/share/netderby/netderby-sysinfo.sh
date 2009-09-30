#!/bin/bash

BASEDIR_REL="`dirname $0`"
BASEDIR=`cd $BASEDIR_REL; pwd`

ENVIRONMENT_FILE="$BASEDIR/environment"
if [ ! -f "$ENVIRONMENT_FILE" ]; then
    echo "Problem, cannot find \"$ENVIRONMENT_FILE\""
    exit 1
fi

. $ENVIRONMENT_FILE

if [ "X$NIMBUS_NETDERBY_SYSINFO" = "X" ] ; then
    echo "Problem, unexpected problem with environment, no NIMBUS_NETDERBY_SYSINFO ?"
    exit 1
fi

NIMBUS_NETDERBY_SYSINFO="$NIMBUS_NETDERBY_SYSINFO $@"
eval $NIMBUS_NETDERBY_SYSINFO

