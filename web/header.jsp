<%-- 
    Document   : header
    Created on : 20 déc. 2012, 11:59:17
    Author     : Tony
--%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="css/ecran.css">
        <link rel="stylesheet" href="css/redmond/jquery-ui-1.9.2.custom.css">
        <script type="text/javascript" src="js/jquery.js"></script>
        <script type="text/javascript" src="js/jquery-ui-1.9.2.custom.js"></script>
        <script type="text/javascript" src="js/blockUI.js"></script>
        <script type="text/javascript" src="js/main.js"></script>
        <title>FreeArt</title>
    </head>
    <body>
        <div id="div_login" title="Connexion à votre compte utilisateur">
            <form id="form_login">
                <label for="txt_login">Login : </label>
                <input type="text" id="txt_login" name="txt_login" />
                
                <br /><br />
                
                <label for="txt_password">Mot de passe : </label>
                <input type="password" id="txt_password" values="txt_password" />
            </form>
        </div>
        <img id="logo" src="img/FreeArt.png" />
        <header>
            
            <h1>
               Contribuez à l'art en ligne
            </h1>
            <div id="searchfield">
                <label for="search">Rechercher</label>
                <input type="text" placeholder="Entrez un mot clé" id="searc" name="search" />
                <img src="img/login.png" class="icon onclick" id="login" />
            </div>
        </header>

