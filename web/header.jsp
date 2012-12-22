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
        <script type="text/javascript" src="js/jquery.js"></script>
        <script type="text/javascript" src="js/blockUI.js"></script>
        <script type="text/javascript" src="js/main.js"></script>
        <title>FreeArt</title>
    </head>
    <body>
        <div id="div_login">
            <h2 align="center">Connexion à votre compte utilisateur</h2>
            <form id="form_login">
                <label for="txt_login">Login : </label>
                <input type="text" id="txt_login" name="txt_login" />
                
                <br />
                
                <label for="txt_password">Mot de passe : </label>
                <input type="password" id="txt_password" values="txt_password" />
            </form>
            <br /><br />
            
            <input type="button" value="Se connecter" style="float: right;" onclick="logUser()" />
            <a href="#" style="float: left;">Pas encore inscrit ?</a>
        </div>
        <header>
            <h1>
                FreeArt : Contribuez à l'art en ligne
            </h1>
            <div id="searchfield">
                <label for="search">Rechercher</label>
                <input type="text" placeholder="Entrez un mot clé" id="searc" name="search" />
                <img src="img/login.png" class="icon onclick" id="login" />
            </div>
        </header>

