Page Title: Central Authentication Service

#Object Definitions
====================================================================================
txt_header      css     body.login-page header h1

inp_username    css     #username
inp_password    css     #password
btn_signin      css     button.btn-primary

txt_errormsg	css		div.error-msg.block-msg
====================================================================================

@all
--------------------------------
txt_errormsg
    above: inp_username > 10 px
    
@fail
--------------------------------
txt_errormsg
    below: inp_username > 10 px