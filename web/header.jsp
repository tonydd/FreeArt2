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
        <script type="text/javascript" src="js/jquery.nicescroll.min.js"></script>
        <script type="text/javascript" src="js/main.js"></script>
        <title>FreeArt</title>
    </head>
    <body>
        
        
        <div id="div_login" title="Connexion à votre compte utilisateur">
            <form id="form_login">
                <label for="txt_login">Login : </label>
                <input type="text" id="txt_login" name="txt_login" value="Admin" />
                
                <br /><br />
                
                <label for="txt_password">Mot de passe : </label>
                <input type="password" id="txt_password" values="txt_password" value="1234" />
            </form>
        </div>
        
        <div id="div_upload" title="Uploader une nouvelle image">
            <form id="form_upload" method="post" enctype="multipart/form-data">
                <table style="width: auto;">
                    <tr>
                        <td><label for="txt_title">Titre de l'image : </label></td>
                        <td valign="right"><input type="text" id="txt_title" name="txt_title"/> </td>
                    </tr>
                    <tr>
                        <td><label for="id_categorie">Catégorie : </label></td>
                        <td valign="right">
                            <select id="id_categorie" name="id_categorie">
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td><label for="desc">Description : </label></td>
                        <td valign="right"><input type="text" id="desc" name="desc" width="200" height="50" /></td>
                    </tr>
                    <tr>
                        <td><label for="file_picture">Choix de la photo : </label></td>
                        <td valign="right"><input type="file" id="file_picture" name="file_picture" /></td>
                    </tr>
                </table>
                <input type="hidden" id="action" name="action" value="upload" />
            </form>
        </div>
        
        <div id="div_menu" class="hide">
            <ul>
                <li>Voir mon profil</li>
                <li onclick='$( "#div_upload" ).dialog( "open" ); toggleMenu();'>Uploader une image</li>
                <li onclick="logOut()">Se déconnecter</li>
            </ul>
        </div>
        
        <header>
            <!--<img id="logo" src="img/FreeArt.png" />-->
            <h1 style="font-variant: small-caps; font-style: oblique ;">
               FreeArt : Contribuez à l'art en ligne
            </h1>
            <div id="searchfield">
                <label for="search">Rechercher</label>
                <input type="text" placeholder="Entrez un mot clé" id="searc" name="search" />
                <img src="img/login.png" class="icon onclick" id="login" onclick="showLoginModal()"/>
            </div>
        </header>

