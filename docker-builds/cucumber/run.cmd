@echo.
@echo --------------------------
@echo running cucumber tests
@echo --------------------------

docker compose up --abort-on-container-exit --exit-code-from cucumber

@echo.
@echo --------------------------
@echo exposing post run database image
@echo --------------------------
docker commit cucumber-db-1 metasfresh/metas-db:local-postcucumber
@echo.
@docker images --filter=reference="metasfresh/metas-db*postcucumber"
@echo.