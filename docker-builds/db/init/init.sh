#!/bin/bash
set -e

add_pg_stat_statements_extension=${ADD_PG_STAT_STATEMENTS_EXTENSION:-n}

echo ""
echo "==================="
echo " Creating role ..."
echo "==================="
psql -v ON_ERROR_STOP=1 --username=postgres <<- EOSQL
CREATE ROLE metasfresh LOGIN ENCRYPTED PASSWORD 'metasfresh' SUPERUSER INHERIT NOCREATEDB NOCREATEROLE;
EOSQL
echo "==========="
echo " ... done!"
echo "==========="

echo ""
echo "======================================="
echo " Creating database and permissions ..."
echo "======================================="
psql -v ON_ERROR_STOP=1 --username=postgres <<- EOSQL
CREATE DATABASE metasfresh WITH OWNER = metasfresh;
GRANT ALL PRIVILEGES ON DATABASE metasfresh to metasfresh;
EOSQL
echo "==========="
echo " ... done!"
echo "==========="

echo ""
echo "==================="
echo "Restoring pgdump"
echo "==================="

# running without "--exit-on-error" because with our current dumps we get "ERROR:  schema "public" already exists"
# also disabling "fail on error" because pg_restore might return with a non-zero exit status
set +e
pg_restore -Fc --username metasfresh --dbname metasfresh /metasfresh.pgdump
set -e

echo "=========="
echo " ...done!"
echo "=========="

activate_extensions()
{
	if [ "${add_pg_stat_statements_extension}" != "n" ]; then
		# needs shared_preload_libraries = 'pg_stat_statements'	in postgresql.conf
		echo "==========================================="
		echo " activate pg_stat_statements extension ..."
		echo "==========================================="
		psql -v ON_ERROR_STOP=1 --username=postgres <<- EOSQL
CREATE EXTENSION IF NOT EXISTS pg_stat_statements;
EOSQL
		echo "==========="
		echo " ... done!"
		echo "==========="
	fi
}

activate_extensions
