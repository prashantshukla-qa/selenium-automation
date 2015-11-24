Page Title: Coach: My Account

#Object Definitions
====================================================================================
btn_register           css      .sign-in-button>span
btn_login              xpath      //*[@id="dwfrm_login"]/div[1]/div/div[2]/span

# Registration Page Objects
inp_FirstName          id          dwfrm_profile_customer_firstname
inp_LastName           id          dwfrm_profile_customer_lastname
inp_Email              id          dwfrm_profile_customer_email
inp_ConfirmEmail       id          dwfrm_profile_customer_emailconfirm
inp_Password           id          dwfrm_profile_login_password
inp_ConfirmPassword    id          dwfrm_profile_login_passwordconfirm
btn_SingUp             name        dwfrm_profile_confirm

# Login Page Objects
inp_LoginEmail         css         input[id^='dwfrm_login_username']
inp_LoginPassword      css         #dwfrm_login_password
btn_SignIn             name        dwfrm_login_login
txt_ErrorMessage       css         #dwfrm_login > div.login-second.visible > div
txt_emailErrorMsg      xpath       //*[@id="dwfrm_login"]/div[2]/fieldset/div[1]/div/span
txt_passwordErrorMsg    css         #dwfrm_login > div.login-second.visible > fieldset > div:nth-child(2) > div > span

modal_registration     xpath       //div[contains(@class,'registration')]//div[contains(@style,'display: block')]
txt_accountCreated     xpath       //div[contains(@class,'registration-step-two')]/p[contains(text(),'#{text}')]

inp_address1           id          dwfrm_profile_address_address1
btn_skip               css         .skip-button


====================================================================================

@mobile
--------------------------------
btn_register
    above: btn_login > 10 px