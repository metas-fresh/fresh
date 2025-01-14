@echo off

set "qualifier=local"
set "pubregistry=metasfresh"
set /P version=<docker-builds/version.info
for /F "tokens=*" %%g in ('powershell -Command "& {Get-Date}"') do (set buildtime=%%g)
for /F "tokens=*" %%g in ('powershell -Command "& {Get-Date -Format """"yyMMddHHmm""""}"') do (set buildnr=%%g)

if not exist docker-builds\mvn\local-settings.xml ( copy docker-builds\mvn\settings.xml docker-builds\mvn\local-settings.xml)

(
	echo build.name=local windows
	echo build.system=%ComputerName%
	echo build.time=%buildtime%
	echo build.user=%USERNAME%
	echo build.version=%version%.3-%qualifier%.%buildnr%
	echo build.number=%buildnr%
) > docker-builds/metadata/build-info.properties

(
	echo git.remote.origin.url=https\://github.com/metasfresh/metasfresh.git
	echo git.branch=n/a
	echo git.commit.id=n/a
	echo git.commit.message=n/a
) > docker-builds/metadata/git.properties

echo.
echo --------------------------
echo declaring metadata
echo --------------------------
echo.
type docker-builds\metadata\build-info.properties
echo.
type docker-builds\metadata\git.properties
echo.
@echo on


@echo.
@echo --------------------------
@echo building maven artifacts
@echo --------------------------

docker build -f docker-builds/Dockerfile.common --secret id=mvn-settings,src=docker-builds/mvn/local-settings.xml -t %pubregistry%/metas-mvn-common:%qualifier% . || @goto error
docker build -f docker-builds/Dockerfile.backend --secret id=mvn-settings,src=docker-builds/mvn/local-settings.xml -t %pubregistry%/metas-mvn-backend:%qualifier% . || @goto error
docker build -f docker-builds/Dockerfile.backend.dist -t %pubregistry%/metas-mvn-backend-dist:%qualifier% . || @goto error
docker build -f docker-builds/Dockerfile.camel --secret id=mvn-settings,src=docker-builds/mvn/local-settings.xml -t %pubregistry%/metas-mvn-camel:%qualifier% . || @goto error
docker build -f docker-builds/Dockerfile.camel.dist -t %pubregistry%/metas-mvn-camel-dist:%qualifier% . || @goto error

docker build -f docker-builds/Dockerfile.junit --secret id=mvn-settings,src=docker-builds/mvn/local-settings.xml -t %pubregistry%/metas-junit:%qualifier% . || @goto error
docker build -f docker-builds/Dockerfile.camel.junit --secret id=mvn-settings,src=docker-builds/mvn/local-settings.xml -t %pubregistry%/metas-camel-junit:%qualifier% . || @goto error
docker build -f docker-builds/Dockerfile.cucumber --secret id=mvn-settings,src=docker-builds/mvn/local-settings.xml -t %pubregistry%/metas-cucumber:%qualifier% . || @goto error


@echo.
@echo --------------------------
@echo building deployables
@echo --------------------------

docker build -f docker-builds/Dockerfile.backend.api -t %pubregistry%/metas-api:%qualifier% . || @goto error
docker build -f docker-builds/Dockerfile.backend.app -t %pubregistry%/metas-app:%qualifier% . || @goto error
docker build -f docker-builds/Dockerfile.camel.externalsystems -t %pubregistry%/metas-externalsystems:%qualifier% . || @goto error
docker build -f docker-builds/Dockerfile.camel.edi -t %pubregistry%/metas-edi:%qualifier% . || @goto error
docker build -f docker-builds/Dockerfile.frontend -t %pubregistry%/metas-frontend:%qualifier% . || @goto error
docker build -f docker-builds/Dockerfile.mobile -t %pubregistry%/metas-mobile:%qualifier% . || @goto error
docker build -f docker-builds/Dockerfile.db-init -t %pubregistry%/metas-db:%qualifier% . || @goto error
docker build -f docker-builds/Dockerfile.db-migration-tool -t %pubregistry%/metas-db-migration-tool:%qualifier% . || @goto error
docker build -f docker-builds/Dockerfile.db-preloaded -t %pubregistry%/metas-db:%qualifier%-preloaded . || @goto error

docker build -f docker-builds/Dockerfile.procurement.backend --secret id=mvn-settings,src=docker-builds/mvn/local-settings.xml -t %pubregistry%/metas-procurement-backend:%qualifier% . || @goto error
docker build -f docker-builds/Dockerfile.procurement.nginx -t %pubregistry%/metas-procurement-nginx:%qualifier% . || @goto error
docker build -f docker-builds/Dockerfile.procurement.rabbitmq -t %pubregistry%/metas-procurement-rabbitmq:%qualifier% . || @goto error
docker build -f docker-builds/Dockerfile.procurement.frontend -t %pubregistry%/metas-procurement-frontend:%qualifier% . || @goto error

@echo --------------------------
@echo building classic-compatible deployables
@echo --------------------------
docker build -f docker-builds/Dockerfile.backend.api.compat -t %pubregistry%/metas-api:%qualifier%-compat . || @goto error
docker build -f docker-builds/Dockerfile.backend.app.compat -t %pubregistry%/metas-app:%qualifier%-compat . || @goto error
docker build -f docker-builds/Dockerfile.mobile.compat -t %pubregistry%/metas-mobile:%qualifier%-compat . || @goto error
docker build -f docker-builds/Dockerfile.frontend.compat -t %pubregistry%/metas-frontend:%qualifier%-compat . || @goto error


:success
@echo.
@echo --------------------------
@echo success
@echo --------------------------
@docker images --filter=reference="%pubregistry%/metas-*"
@echo.
@exit

:error
@echo.
@echo --------------------------
@echo failure
@echo --------------------------