#!/bin/sh

cd /opt/code-base/internal-server-1/taskflow/
git remote update
LOCAL=$(git rev-parse @)
REMOTE=$(git rev-parse @{u})
BASE=$(git merge-base @ @{u})

if [ $LOCAL = $REMOTE ]; then
      echo "Up-to-date"
elif [ $LOCAL = $BASE ]; then
      echo "Need to pull"
      git stash save && git pull --rebase && git stash pop
      NEWLOCAL=$(git rev-parse @)
      if [ $NEWLOCAL = $REMOTE ]; then
        echo "Pull successfull"
        service php5-fpm restart
        service nginx restart
      fi
elif [ $REMOTE = $BASE ]; then
      echo "Need to push"
else
      echo "Diverged"
fi
