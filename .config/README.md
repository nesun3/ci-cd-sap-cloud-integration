>Follow External Parameter Configurations for each iflow in their respective folders above.

## Prerequisite


To use this integration artifacts, you will need the following:

1. Download the Integration Package ZIP archive from the GitHub Repo and import Integration Package into your tenant.
2. Create an OAuth client (Client Credentials grant type) on the tenant:
    - Navigate to your SAP BTP cockpit of the Cloud Foundry subaccount, go to 'Services', then 'Service Marketplace' and select 'Process Integration'.
    - Create a new instance with service plan 'api' and with the following configuration:
        - Grant-types: 'client_credentials'
        - Roles: `AuthGroup_IntegrationDeveloper`, `AuthGroup_Administrator`, `AuthGroup_BusinessExpert`
    - Create a Service Key and copy the entire JSON text to your clipboard to use in the Step 4 for Create `OAuth2 Client Credentials`.
    - *[Optional]* Repeat the above steps for the Target tenant (required only for Transport Integration Contents iflow).
3. GitHub Repo Creation:
    - If you already have a GitHub account you can skip this step , otherwise Signup for the new Account.
    - Once account is created, login to GitHub and Create `Personal Access Token` (classic). Copy the new token to your clipboard to use in the Step 4 for Create `Secure Parameter`.
    - Create a new private repository with appropriate name in GitHub, example integration-content-repo or sap-cloud-integration-artifacts.
4. Create the User Credentials:
    - Manage `Security Material` -> Add Create `OAuth2 Client Credentials`. **Deploy** the credentials.
    - *[Optional]* Repeat the above steps for the Target tenant (required only for Transport Integration Contents iflow).
    - Manage `Security Material` -> Add Create `Secure Parameter`. **Deploy** the credentials.
5. **[Optional]** Microsoft Teams Channel Webhook configuration:
    - *Configure only in case you want to send Notification to your MS Teams Channel.*
    - Create Incoming Webhooks for your MS Teams Channel. Copy and store the webhook address.

