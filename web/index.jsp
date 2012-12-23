<%-- 
    Document   : index
    Created on : 14 déc. 2012, 15:49:00
    Author     : Tony
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="header.jsp" ></jsp:include>
<jsp:include page="left.jsp" ></jsp:include>
    <div id="mainContainer">
            <h2>
                Galleries d'images
            </h2>
        <input type="text" id="CACA" />
            <%
            //<a href="image.jpg" class="preview" title="Great looking landscape">Roll over to preview</a>
            //<a href="1.jpg" class="preview"><img src="1s.jpg" alt="gallery thumbnail" /></a>
                for (int i = 0; i < 20; i++)
                {
                    out.println("<div class=\"display\" >");
                    out.println("<img src=\"img/2012-12-10 12.53.06.jpg\" class=\"display\" /> ");
                    out.println("<p class=\"comment\">Commenter</p>");
                    out.println("<br /> Un petit burger pozé");
                    out.println("</div>");
                }
            %>
            
        </div>
<jsp:include page="footer.jsp" ></jsp:include>
