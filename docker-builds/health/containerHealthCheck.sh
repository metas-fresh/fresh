#
# %L
# work-javaupdate
# %%
# Copyright (C) 2024 metas GmbH
# %%
# This program is free software: you can redistribute it and/or modify
# it under the terms of the GNU General Public License as
# published by the Free Software Foundation, either version 2 of the
# License, or (at your option) any later version.
#
# This program is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
# GNU General Public License for more details.
#
# You should have received a copy of the GNU General Public
# License along with this program. If not, see
# <http://www.gnu.org/licenses/gpl-2.0.html>.
# L%
#

containerName=$1

echo ""
echo ""
echo "========================================================="
echo " $containerName health check ..."
echo "---------------------------------------------------------"

timeout 300s sh -c "until docker ps | grep $containerName | grep -q '(healthy)'; do echo 'Waiting for container to be healthy...'; sleep 30; done"
status=$?
echo "Status: $status"
if [ $status -eq 124 ] #timed out
then
  echo "---------------------------------------------------------"
  echo " start log of unhealthy $containerName"
  echo "---------------------------------------------------------"
  docker compose -f ../compose/compose.yml logs --tail 1000 "$containerName"
  echo ""
  echo "---------------------------------------------------------"
  echo " end log of unhealthy $containerName"
  echo "---------------------------------------------------------"
  echo ""
  echo "---------------------------------------------------------"
  echo " $containerName health check NOT OK"
  echo "========================================================="

  exit 1
else
  echo ""
  echo "---------------------------------------------------------"
  echo " $containerName health check OK"
  echo "========================================================="

  exit 0
fi

