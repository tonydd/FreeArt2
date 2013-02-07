<%-- 
    Document   : header
    Created on : 20 d�c. 2012, 11:59:17
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
        
        <div id="spinner" class="hide">
            <img src="img/spinner2-black.gif" style="width: 50px;" />
        </div>
        
        <!-- ******************* HTML FENETRES MODALES ************************** -->
        
        <div id="div_login" title="Connexion � votre compte utilisateur">
            <form id="form_login">
                <label for="txt_login">Login : </label>
                <input type="text" id="txt_login" name="txt_login" value="Admin" />
                
                <br /><br />
                
                <label for="txt_password">Mot de passe : </label>
                <input type="password" id="txt_password" values="txt_password" value="1234" />
            </form>
        </div>
        
        <div id="div_subscribe" title="Inscription � FreeArt">
            <form id="form_subscribe">
                <table>
                    <tr>
                        <td><label for="txt_create_login">Login : </label></td>
                        <td><input type="text" id="txt_create_login" name="txt_create_login" value="" /></td>
                    </tr>
                    <tr>        
                        <td><label for="txt_create_password">Mot de passe : </label></td>
                        <td><input type="password" id="txt_create_password" value="" /></td>
                    </tr>
                    <tr>
                        <td><label for="txt_create_password2">Confirmez mot de passe : </label></td>
                        <td><input type="password" id="txt_create_password2"  value="" /></td>
                    </tr>
                    <tr>
                        <td><label for="txt_create_mail">Adresse e-mail </label></td>
                        <td><input type="text" id="txt_create_mail"  value="" /></td>
                    </tr>
                </table>
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
                        <td><label for="id_categorie">Cat�gorie : </label></td>
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
        
        <div id="div_delete_user" title="Etes-vous s�r de vouloir supprimer votre compte utilisateur ?">
            <p><span class="ui-icon ui-icon-alert" style="float: left; margin: 0 7px 20px 0;"></span>Cette op�ration est irr�v�rsible, elle d�truira aussi toutes les photos que vous avez upload�, <br />Etes-vous s�r de vouloir continuer ?</p>
        </div>
        
        <div id="show_panier" title="Contenu du panier">
            <table rules="all" border="1">
                <thead>
                    <tr>
                        <th>
                            Image
                        </th>
                        <th>
                            Cat�gorie
                        </th>
                        <th>
                            Action
                        </th>
                    </tr>
                </thead>
                <tbody id="basketContent"></tbody>
            </table>
        </div>
        
        <div id="commentPanel" title="Commentaires">
            <div id="comments"></div>
            <div id="post_comment">
                <h3>Votre commentaire : </h3>
                <textarea id="txt_comment">

                </textarea>
            </div>
        </div>
        
        <!-- **************** MENU DEROULANT ACTIONS UTILISATEUR ********************************** -->
        
        <div id="div_menu" class="hide">
            <ul>
                <li class="menuItem" onclick="showUserProfil();">Voir mon profil</li>
                <li class="menuItem" onclick='$( "#div_upload" ).dialog( "open" );'>Uploader une image</li>
                <li class="menuItem" onclick='managePictures();'>G�rer mes images</li>
                <li class="menuItem" onclick="logOut()">Se d�connecter</li>
            </ul>
        </div>
        
        <header>
            <!--<img id="logo" src="img/FreeArt.png" />-->
            <h1 style="font-variant: small-caps; font-style: oblique ;" id="mainTitle" onclick="index();">
               FreeArt : Contribuez � l'art en ligne
            </h1>
            <div id="searchfield">
                <label for="search">Rechercher</label>
                <input type="text" placeholder="Entrez un mot cl�" id="search" name="search" onkeyup="search(this);" />
                <img src="img/login.png" class="icon onclick" id="login" onclick="showLoginModal()"/>
            </div>
        </header>

