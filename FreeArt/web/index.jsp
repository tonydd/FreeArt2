<%-- 
    Document   : index
    Created on : 14 déc. 2012, 15:49:00
    Author     : Tony
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="css/ecran.css">
        <script type="text/javascript" src="js/main.js"></script>
        <title>FreeArt</title>
    </head>
    <body>
        <header>
            <h1>
                FreeArt : Contribuez à l'art en ligne
            </h1>
            <div id="searchfield">
                <label for="search">Rechercher</label>
                <input type="text" placeholder="Entrez un mot clé" id="searc" name="search" />
            </div>
        </header>
        
        <div id="left">
            <h2>
                Catégories
            </h2>
            <%
                for (int i = 0; i< 10; i++)
                {
                    out.println("<p style=\"margin-left: 2%;\"> Element  " + i + "</p>");
                }
            %>
        </div>
        
        <div id="mainContainer">
            <h2>
                Galleries d'images
            </h2>
            
        </div>
        
        <footer>
            <div id="detailsPanier">
                Articles dans le panier : <label id="panierCount">0</label>
            </div>
            
            <div id="about">
                Anthony DEN DRIJVER, Jose DUQUE GORDILLO, Thomas STOCKER @ P51 2012
            </div>
        </footer>
    </body>
</html>
