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
      And HE I verify the text "File transfers will occur on selected days every week between approximately 12 AM and 1 AM (ET)." is displayed
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
          And HE I verify the Host text box has the value: "209.97.159.244"
          And HE I verify the Port text box has the value: "22"
          And HE I verify the Path text box has the value: "/uploads"
          And HE I verify the User Name text box has the value: "sftpme"
          When HE I delete the SFTP Data Transfer connection
          Then HE I verify that the success toast that says "Configuration deleted" is displayed
          Given SP I am logged in to the Admin page as a Support user
          When SP I select "The University of Alabama" from the institution dashboard
          And SP I go to the log history page
          Then SP I verify that it is displayed an entry with action "PurpleHE Automation Deleted AMExportConfig" and the following keys
          |id:|scid:|modifiedBy:|secretName:|createdDate:|lastModified:|


        @MATCH-4973 @MATCH-4947
        Scenario: As an HE admin associated with an HE institution that has an active AMPLUS subscription and that has
        enabled Server Fingerprint Verification for my SFTP Data Transfer for Student Connections, I want the ability to
        preview the detected fingerprint at both set-up as well as if a mismatch has been detected following confirmation,
        so that I can take appropriate action.
          Given HE I am logged in to Intersect HE as user type "administrator"
          And HE I setup a SFTP connection with the following data
            |host          |port|path    |userName|password         |transferFrequency  |checkFingerPrintToVerifyServer|
            |209.97.159.244|22  |/uploads|sftpme  |bruh-you-can-SFTP|mon,tue,wed,thu,fri|no                            |
          And HE I enable the server fingerprint verification
          Then HE I verify that the warning toast that says "Configuration saved with errors" is displayed
          Then HE I verify that the new fingerprint detected alert box has the text "New fingerprint detected. Please take action to unblock file transfers."
          And HE I verify that the new fingerprint detected alert box has the text "Confirm with your administrator that this is your server's fingerprint:"
          And HE I verify that the new fingerprint detected alert box has the text "c0:4a:58:71:41:f8:44:75:94:7e:2c:a1:d8:9f:f0:b7"
          And HE I verify that the new fingerprint detected alert box has the text "If your server uses load balancing, consider disabling Server Fingerprint Verification. If you suspect malicious activity, consider editing or deleting this configuration."
          When HE I select the Yes, Fingerprint is Correct link
          Then HE I verify that the success toast that says "Configuration saved successfully" is displayed
          And HE I verify that the server fingerprint verification is enabled
          And HE I verify that the new fingerprint detected alert box is not displayed
          And HE I verify the text "Last Fingerprint Confirmed" is displayed
          And HE I verify the text "c0:4a:58:71:41:f8:44:75:94:7e:2c:a1:d8:9f:f0:b7" is displayed
          Then HE I verify that when clicking on the title link I am redirected to the main page
          When HE I disable the server fingerprint verification
          And HE I enable the server fingerprint verification
          And HE I select the Disable Server Fingerprint Verification link
          Then HE I verify that the success toast that says "Configuration saved successfully" is displayed
          And HE I verify that the server fingerprint verification is disabled
          And HE I verify that the new fingerprint detected alert box is not displayed
          When SP I am logged in to the Admin page as a Support user
          And SP I select "The University of Alabama" from the institution dashboard
          And SP I go to the log history page
          Then SP I verify that it is displayed an entry with action "PurpleHE Automation Edited AMExportConfig" and the following keys
            |serverFingerprintEnabled:|serverFingerprintMismatch:|

        @MATCH-4875
        Scenario:
        As an HE admin associated with an HE account that has an active AMPLUS subscription, I want the ability to edit
        a previously saved Active Match Connections SFTP set-up, so that if anything changes regarding my connection
        details, I can update it accordingly to maintain a successful connection for file transfer.
          Given HE I am logged in to Intersect HE as user type "administrator"
          And HE I setup a SFTP connection with the following data
            |host          |port|path    |userName|password         |transferFrequency  |checkFingerPrintToVerifyServer|
            |209.97.159.244|22  |/uploads|sftpme  |bruh-you-can-SFTP|mon,tue,wed,thu,fri|no                            |
          When HE I go to the Set Edit Connection page
          Then HE I verify that when clicking on the title link I am redirected to the main page
          When HE I go to the Set Edit Connection page
          Then HE I verify the last updated label has the text: "Last updated (.*)by PurpleHE Automation"
          And HE I verify the title of the page is "SFTP Data Transfer"
          And HE I verify the text "Edit Connection" is displayed
          And HE I verify the text "CONNECTION ADDRESS" is displayed
          And HE I verify the text "This is the address of your SFTP server." is displayed
          And HE I verify the Host text box has the value: "209.97.159.244"
          And HE I verify the Port text box has the value: "22"
          And HE I verify the Path text box has the value: "/uploads"
          And HE I verify the text "CONNECTION CREDENTIALS" is displayed
          And HE I verify the text "These are the login details that provide access to your SFTP server." is displayed
          When HE I select the value "SSH Public Key" in authentication method radio buttons
          And HE I verify the text "SSH Public Keys can be used instead of a password to authenticate over secure protocols. Be careful, personal access tokens should be treated as securely as any other password." is displayed
          Then HE I verify the text of the generate ssh key button is "GENERATE KEY"
          When HE I select the value "Password" in authentication method radio buttons
          And HE I verify the text "TRANSFER FREQUENCY" is displayed
          And HE I verify the text "Choose which days of the week Intersect should transfer new data to your SFTP server." is displayed
          And HE I verify that the following check boxes are displayed in transfer frequency section
            |sun|mon|tue|wed|thu|fri|sat|
          And HE I verify the text "File transfers will occur on selected days every week between approximately 12 AM and 1 AM (ET)." is displayed
          And HE I verify the text "IP WHITELISTING" is displayed
          And HE I verify the text "To successfully accept this connection, you may need to add the Hobsons Public IPs to your IP Whitelist." is displayed
          And HE I verify the following ip addresses are displayed in IP WHITELISTING text box
            |52.0.209.46|52.203.133.79|52.204.225.254|
          And HE I verify the text "SERVER FINGERPRINT VERIFICATION" is displayed
          And HE I verify the text "For added security, it is recommended you choose to preview and confirm the fingerprint of the SFTP server you specified." is displayed
          And HE I verify that Check fingerprint to verify server check box is displayed
          And HE I verify the text "For quicker confirmation during set up, it is recommended you ask your administrator for your server's fingerprint beforehand." is displayed
          And HE I verify that the TEST AND SAVE button is displayed
          When HE I select the value "SSH Public Key" in authentication method radio buttons
          And I generate a new SSH Key
          Then I verify a new SSH key was generated
          When HE I select the value "Password" in authentication method radio buttons
          And HE I go to the Main Sftp Connection page
          And HE I go to the Set Edit Connection page
          And HE fill the sftp connection form with the following data
            |host          |port|path     |userName |password         |transferFrequency  |checkFingerPrintToVerifyServer|
            |209.97.159.245|26  |/uploads2|sftpyou  |bruh-you-can-SFTP|mon,tue,wed,thu,fri|no                            |
          And HE I go to the Main Sftp Connection page
          When HE I go to the Set Edit Connection page
          Then HE I verify the Host text box has the value: "209.97.159.244"
          And HE I verify the Port text box has the value: "22"
          And HE I verify the Path text box has the value: "/uploads"
          And HE I verify the User Name text box has the value: "sftpme"
          And HE I setup a SFTP connection with the following data
            |host          |port|path    |userName     |password                |transferFrequency   |checkFingerPrintToVerifyServer|
            |142.93.52.11  |22  |/uploads|nightswatch  |foo-bar-biz-bat-4442332 |mon,tue,wed,thu,fri |yes                           |
          When HE I go to the Set Edit Connection page
          Then HE I verify the Host text box has the value: "142.93.52.11"
          And HE I verify the Port text box has the value: "22"
          And HE I verify the Path text box has the value: "/uploads"
          And HE I verify the User Name text box has the value: "nightswatch"
          When SP I am logged in to the Admin page as a Support user
          And SP I select "The University of Alabama" from the institution dashboard
          And SP I go to the log history page
          Then SP I verify that it is displayed an entry with action "PurpleHE Automation Edited AMExportConfig" and the following keys
            |institutionId:|connectionTestSuccess:|serverFingerprintMismatch:|serverFingerprintEnabled:|