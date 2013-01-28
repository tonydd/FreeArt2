/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

if (!Array.prototype.indexOf)
{
  Array.prototype.indexOf = function(elt /*, from*/)
  {
    var len = this.length;

    var from = Number(arguments[1]) || 0;
    from = (from < 0)
         ? Math.ceil(from)
         : Math.floor(from);
    if (from < 0)
      from += len;

    for (; from < len; from++)
    {
      if (from in this &&
          this[from] === elt)
        return from;
    }
    return -1;
  };
}

 Array.prototype.unset = function(val){
    var index = this.indexOf(val)
    if(index > -1){
        this.splice(index,1)
    }
}

//---------------------------------------------------------------------------------------------------------------------------------------------

$(document).ajaxStart(function()
{
    $.blockUI({ message: '<img src="img/ajax_spinner.gif" />' })
})
.ajaxStop($.unblockUI);

var panier = Array();

$(document).ready(function () 
{
    
    $("img.display").mouseenter(function ()
    {
        $(this).next().fadeIn(250);
    });
    
    $("img.display").mouseout(function ()
    {
       $(this).next().fadeOut(250); 
    });
    
    
    /* --- Chargement des catégories --- */
    
    $.get(
        'MainController',
        {
            data : "categories"
        },
        function(data)
        {
            $("ul#categories").html(data);
        }
    );
    
        
    /* --- TEMP --- Chargement jeu essai images --- */
   $.get(
        'MainController',
        {
            data : "images"
        },
        function(data)
        {
            $("div#mainContainer").append(data);
        }
    );
    
     $( "#div_login" ).dialog({
            autoOpen: false,
            height: 300,
            width: 500,
            modal: true,
            buttons: {
                "Pas encore inscrit ?": function() {
                    $( this ).dialog( "close" );
                },
                "Se connecter" : function() {
                    logUser();
                }
            },
            close: function() {
                $( this ).dialog( "close" );
            }
        });
        
        $( "#div_upload" ).dialog({
            autoOpen: false,
            height: 400,
            width: 600,
            modal: true,
            buttons: {
                "Annuler": function() {
                    $( this ).dialog( "close" );
                },
                "Soumettre" : function() {
                    var form = document.getElementById('form_upload');//new FormData($('#form_file'));
                    var formData = new FormData(form);
                    console.log(formData);
                    sendPic(formData);
                }
            },
            close: function() {
                $( this ).dialog( "close" );
            }
        });
});

function unblockUI()
{
    $.unblockUI();
}

function logUser()
{
    var login = $("#txt_login").val();
    var passwd = $("#txt_password").val();
    $.post(
        "MainController",
        {
            action : "login",
            txt_login: login,
            txt_password: passwd
        },
        function(data)
        {
            console.log(data);
            if (data == "OK")
            {
                $( "#div_login" ).dialog( 'close' );
                document.getElementById('login').setAttribute('src', "img/ok.png");
                document.getElementById('login').onclick = toggleMenu;
                window.setTimeout(function() {showSuccessGrowlDiv("Login", "Vous êtes maintenant identifiés")}, 500);                
            }
            else
            {
                $( "#div_login" ).dialog( 'close' );
                alert("LOGIN FAILED");
            }
        }
    );
}

function logOut()
{
    toggleMenu();
    $.post(
        "MainController",
        {
            action : "logout"
        },
        function(data)
        {
            document.getElementById('login').setAttribute('src', "img/login.png");
            document.getElementById('login').onclick = showLoginModal;
            window.setTimeout(function() {showSuccessGrowlDiv("Logout", "Vous êtes maintenant déconnecté")}, 500);                
        }
    );
}

function sendPic(formData)
{
    $.ajax({
            url: 'MainController',
            type: 'POST',
            xhr: function() { 
                myXhr = $.ajaxSettings.xhr();
                return myXhr;
            },
            success: function(data)
            {
                $( "#div_upload" ).dialog( 'close' );
                window.setTimeout(function() {showSuccessGrowlDiv(data, "Uploadé avec succès")}, 500);
            },
            data: formData,
            cache: false,
            contentType: false,
            processData: false
        });
}

function addToPanier(photo)
{
    if (panier.indexOf(photo) == -1)
    {
        panier.push(photo.id);
        showSuccessGrowlDiv(photo.id, "Ajouté avec succès au panier !" );
    }
    else
    {
        panier.unset(photo.id);
        showSuccessGrowlDiv(photo.id, "Retiré avec succès au panier !" );
    }
    
    document.getElementById('panierCount').innerHTML = panier.length;
       
}

function toggleMenu()
{
    if ($("div#div_menu").hasClass('hide'))
    {
        $("div#div_menu").removeClass('hide');
        $("div#div_menu").animate(
        {
            'height' : '200px',
            'opacity' : '0.7'
            },
            500,
            function()
            {               
            }
        );
    }
    else
    {
        $("div#div_menu").animate(
        {
            'height' : '0px',
            'opacity' : '0'
            },
            500,
            function()
            {
                $(this).addClass('hide');
            }
        );
    }
}

function showSuccessGrowlDiv(titleText, contentText)
{
    var div = document.createElement('div');
    var title = document.createElement('h2');
    var content = document.createElement('p');
    var img = document.createElement('img');
    
    title.appendChild(document.createTextNode(titleText));
    content.appendChild(document.createTextNode(contentText));
    img.src = 'img/ok.png';
    $(img).css({'float' : 'left'});
    
    div.appendChild(img);
    div.appendChild(title);
    div.appendChild(content);
    
    $.blockUI({ 
            message: $(div), 
            fadeIn: 700, 
            fadeOut: 700, 
            timeout: 2000, 
            showOverlay: false, 
            centerY: false, 
            css: { 
                width: '350px', 
                top: '10px', 
                left: '', 
                right: '10px', 
                border: 'none', 
                padding: '5px', 
                backgroundColor: '#000', 
                'border-radius': '10px', 
                opacity: .6, 
                color: '#fff' 
            } 
        });
}

function showLoginModal()
{
    $( '#div_login' ).dialog( 'open' );
}

function showUploadModal()
{
    
}
