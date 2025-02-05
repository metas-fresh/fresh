@from:cucumber
@ghActions:run_on_executor1
Feature: Test sending an outbound mail

  Background: Initial Data
    Given infrastructure and metasfresh are running
    And metasfresh has date and time 2021-04-16T13:30:13+02:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION

  @from:cucumber
  Scenario: SMTP
    Given email successfully sent
      | mailbox.type | mailbox.smtp.host$env | mailbox.smtp.port | mailbox.smtp.auth | mailbox.from$env | mailbox.smtp.username$env | mailbox.smtp.password$env | mailbox.smtp.startTLS | to$env         | subject      | message      |
      | smtp         | TEST_SMTP_HOST        | 587               | true              | TEST_SMTP_FROM   | TEST_SMTP_USER            | TEST_SMTP_PASSWORD        | true                  | TEST_SMTP_FROM | test subject | test message |
