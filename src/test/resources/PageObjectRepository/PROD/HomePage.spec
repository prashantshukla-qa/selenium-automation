Page Title: COACH: Global

#Object Definitions
====================================================================================

lnk_country           xpath        (//a[text()='${country}'])[last()]
btn_Account           css          #drop1
txt_userName          xpath         (//span[@id='user-name'])[1]
lnk_accountDropdown   css          #account-dropdown>a
lnk_signOut           css          a[title='Sign Out']
====================================================================================

@all
--------------------------------
btn_lefnav
    right of: lnk_breadcrumb
    aligned vertically: lnk_breadcrumb