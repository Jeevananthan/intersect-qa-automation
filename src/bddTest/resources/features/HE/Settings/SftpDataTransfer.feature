@HE
Feature: HE - Settings - SFTP Data Transfer - As an HE admin user, I should be able to manage SFTP Data Transfer connections

  @MATCH-4793
  Scenario: As an HE admin associated with an HE account that has an active AMPLUS subscription. I want the ability to
  access SFTP set-up for my HE account from my broader Intersect Account Settings, so that global account settings such
  as these are housed in an anticipated and centralized location.
    Given HE I am logged in to Intersect HE as user type "administrator"
    And HE I delete the SFTP Data Transfer connection
    Then HE I verify the text "SFTP Data Transfer" is displayed
    And HE I verify the text "Establish an automated secure file transfer using SFTP for the data listed below." is displayed
    And HE I verify the text "NAVIANCE ACTIVEMATCH" is displayed
    And HE I verify the text "Student Connections" is displayed
    And HE I verify the text "Transfer new ActiveMatch student connections to a location of your choice." is displayed
    And HE I verify the SET UP CONNECTION button is displayed
    Given HE I am logged in to Intersect HE as user type "publishing"
    Then HE I verify the "SFTP Data Transfer" tab is not displayed
    Given HE I am logged in to Intersect HE as user type "limited"
    Then HE I verify the "SFTP Data Transfer" tab is not displayed

    @MATCH-4795
    Scenario: As an HE admin associated with an HE account that has an active AMPLUS subscription,
    I want the ability to initiate Active Match Connections SFTP set-up modal for my HE account from my broader
    Intersect Account Settings, so that set-up is easily accessible from the newly created SFTP stub menu from
    Intersect Account Settings
      Given HE I am logged in to Intersect HE as user type "administrator"
      And HE I delete the SFTP Data Transfer connection
      When HE I go to the Set Up Connection page
      Then HE I verify that when clicking on the title link I am redirected to the main page
      And HE I go to the Set Up Connection page
      And HE I verify the title of the page is "SFTP Data Transfer"
      And HE I verify the text "Set Up Connection" is displayed
      And HE I verify the text "CONNECTION ADDRESS" is displayed
      And HE I verify the text "This is the address of your SFTP server." is displayed
      And HE I verify the Host text box has the value: ""
      And HE I verify the Port text box has the value: "22"
      And HE I verify the Path text box has the value: ""
      And HE I verify the text "CONNECTION CREDENTIALS" is displayed
      And HE I verify the text "These are the login details that provide access to your SFTP server." is displayed
      When HE I select the value "SSH Public Key" in authentication method radio buttons
      And HE I verify the text "SSH Public Keys can be used instead of a password to authenticate over secure protocols. Be careful, personal access tokens should be treated as securely as any other password." is displayed
      And HE I verify GENERATE KEY button is displayed
      When HE I select the value "Password" in authentication method radio buttons
      And HE I verify the User Name text box has the value: ""
      And HE I verify the Password text box has the value: ""
      And HE I verify the text "TRANSFER FREQUENCY" is displayed
      And HE I verify the text "Choose which days of the week Intersect should transfer new data to your SFTP server." is displayed
      And HE I verify that the following check boxes are displayed in transfer frequency section
          |sun|mon|tue|wed|thu|fri|sat|
      And HE I verify the text "File transfers will occur on selected days every week between approximately 12 AM and 1 AM (EDT)." is displayed
      And HE I verify the text "IP WHITELISTING" is displayed
      And HE I verify the text "To successfully accept this connection, you may need to add the Hobsons Public IPs to your IP Whitelist." is displayed
      And HE I verify the following ip addresses are displayed in IP WHITELISTING text box
          |52.0.209.46|52.203.133.79|52.204.225.254|
      And HE I verify the text "SERVER FINGERPRINT VERIFICATION" is displayed
      And HE I verify the text "For added security, it is recommended you choose to preview and confirm the fingerprint of the SFTP server you specified." is displayed
      And HE I verify that Check fingerprint to verify server check box is displayed
      And HE I verify the text "For quicker confirmation during set up, it is recommended you ask your administrator for your server's fingerprint beforehand." is displayed
      And HE I verify that the TEST AND SAVE button is displayed

      @MATCH-4917
      Scenario: As an HE admin associated with an HE account that has an active AMPLUS subscription, I want the ability
      to generate an SSH public key for Hobsons So that I can leverage an alternate authentication path beyond
      entering our SFTP server password.
        Given HE I am logged in to Intersect HE as user type "administrator"
        And HE I delete the SFTP Data Transfer connection
        When HE I go to the Set Up Connection page
        And HE I select the value "SSH Public Key" in authentication method radio buttons
        Then HE I verify the text of the generate ssh key button is "GENERATE KEY"
        When I generate a new SSH Key
        Then I verify a new SSH key was generated
        And HE I verify the text of the re generate ssh key button is "DELETE & REGENERATE KEY"
        And I verify the generated ssh key message that says "Using public key generated (.*)by PurpleHE Automation"
        And HE I verify the text "Copy the generated key shown below immediately. You won't see it again later. Otherwise, you'll need to generate a new one." is displayed
        And HE I verify the text "SSH Public Keys can be used instead of a password to authenticate over secure protocols. Be careful, personal access tokens should be treated as securely as any other password." is displayed

        @MATCH-4876
        Scenario: As an HE admin associated with an HE account that has an active AMPLUS subscription, I want the
        ability to delete a previously saved Active Match Connections SFTP set-up, so that if I no longer the file to
        automatically be pushed via SFTP from intersect, I can stop such a transfer.
          Given HE I am logged in to Intersect HE as user type "administrator"
          And HE I setup a SFTP connection with the following data
          |host          |port|path    |userName|password         |transferFrequency  |checkFingerPrintToVerifyServer|
          |209.97.159.244|22  |/uploads|sftpme  |bruh-you-can-SFTP|mon,tue,wed,thu,fri|no                            |
          Then HE I verify that clicking on GO BACK button it cancels the configuration deletion
          When HE I delete the SFTP Data Transfer connection
          Then HE I verify that the success toast that says "Configuration deleted" is displayed





