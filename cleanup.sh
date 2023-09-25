#!/usr/bin/env bash

# Cleans up java project files
# (i.e. formats them doing things that IDEA can't do)

# shellcheck disable=SC2044
for file in $(find src/org/quak/sheets -type f); do
  # Hooks into default behaviour of vim to allow interactive file confirmation
  # To be precise, if the file gets edited by the below commands
  # vim will not exit because it refuses to exit unless the file has been saved

  # Currently only removes full stops from the end of lines (comments)
	vim "+%s/\(.\+\)\.$/\1" "+q" $file
done
