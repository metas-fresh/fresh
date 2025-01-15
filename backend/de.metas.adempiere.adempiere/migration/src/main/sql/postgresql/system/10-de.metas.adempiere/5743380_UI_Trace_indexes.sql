DROP INDEX IF EXISTS ui_trace_externalid
;

CREATE INDEX ui_trace_externalid ON ui_trace (externalid)
;

DROP INDEX IF EXISTS ui_trace_eventName
;

CREATE INDEX ui_trace_eventName ON ui_trace (eventname)
;

DROP INDEX IF EXISTS ui_trace_caption
;

CREATE INDEX ui_trace_caption ON ui_trace (caption)
;

DROP INDEX IF EXISTS ui_trace_applicationId
;

CREATE INDEX ui_trace_applicationId ON ui_trace (ui_applicationid)
;

DROP INDEX IF EXISTS ui_trace_username
;

CREATE INDEX ui_trace_username ON ui_trace (username)
;

DROP INDEX IF EXISTS ui_trace_deviceId
;

CREATE INDEX ui_trace_deviceId ON ui_trace (ui_deviceid)
;

--
--
--
--
--
--
--

DROP INDEX IF EXISTS api_request_audit_ui_trace_externalid
;

CREATE INDEX api_request_audit_ui_trace_externalid ON api_request_audit (ui_trace_externalid)
;

