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
    //$.blockUI({ message: '<img src="img/ajax_spinner.gif" />' })
    $("div#spinner").toggleClass('hide');
})
.ajaxStop(function()
{
    $("div#spinner").toggleClass('hide');
});

var panier = Array();
var lastImageId = 0; var lastImageData = Array();

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
    
    $("div#mainContainer").niceScroll();
    
    
    /* --- Chargement des catégories pour le menu de gauche --- */
    
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
        
    /* --- Chargement des catégories pour le menu déroulant upload --- */
    
    $.get(
        'MainController',
        {
            data : "categories_select"
        },
        function(data)
        {
            $("#id_categorie").html(data);
        }
    );
    
        
    /* ---Chargement des images les plus récentes au lancement --- */
     refreshMain();
     
     $( "#div_subscribe" ).dialog({
            autoOpen: false,
            height: 300,
            width: 500,
            modal: true,
            buttons: {
                "Annuler": function() {
                    $( this ).dialog( "close" );
                },
                "Inscription" : function() {
                    subscribe();
                }
            },
            close: function() {
                $( this ).dialog( "close" );
            }
        });
    
     $( "#div_login" ).dialog({
            autoOpen: false,
            height: 300,
            width: 500,
            modal: true,
            buttons: {
                "Pas encore inscrit ?": function() {
                    $( this ).dialog( "close" );
                    $( '#div_subscribe' ).dialog( 'open' );
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
                window.setTimeout(function() {showGrowlDiv('success', "Login", "Vous êtes maintenant identifiés")}, 250);                
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
            window.setTimeout(function() {showGrowlDiv("success", "Logout", "Vous êtes maintenant déconnecté")}, 250);                
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
                window.setTimeout(function() {showGrowlDiv("success",data, "Uploadé avec succès")}, 250);
                refreshMain();
            },
            data: formData,
            cache: false,
            contentType: false,
            processData: false
        });
}

function addArticleToPanier(photo)
{
    if (panier.indexOf(photo.id) == -1)
    {
        panier.push(photo.id);
        showGrowlDiv("success","Notification", "L'image à été ajoutée avec succès au panier !" );
    }
    else
    {
        panier.unset(photo.id);
        showGrowlDiv("success","Notification", "L'image à été retirée avec succès au panier !" );
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
            'height' : '205px',
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

function showGrowlDiv(type, titleText, contentText)
{
    var div = document.createElement('div');
    var title = document.createElement('h2');
    var content = document.createElement('p');
    var img = document.createElement('img');
    
    title.appendChild(document.createTextNode(titleText));
    content.appendChild(document.createTextNode(contentText));
    
    if (type === 'success')
        img.src = 'img/ok.png';
    else if (type === 'error')
        img.src = 'img/ko.png';
    
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
                top: '5.5%', 
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

function refreshMain()
{
    $.get(
        'MainController',
        {
            data : "images"
        },
        function(data)
        {
            $("div#mainContainer").html(data);
            makeTooltip();
        }
    );
}

function getByCategorie(catId)
{
    $.get(
        'MainController',
        {
            data : "images",
            categorie : catId
        },
        function(data)
        {
            $("div#mainContainer").html(data);
        }
    );
}

function makeTooltip()
{/*
    $( document ).tooltip({
      items: "img.display",
      track: true,
      content: function() 
      {
        var photoId = $( this ).attr('id');
        if (photoId != lastImageId)
        {
            $.get(
                'MainController',
                {
                    data : "details",
                    imgId : photoId
                },
                function(data)
                {
                    lastImageId = photoId;
                    var split = data.split("$");//NOM - DATE - DESC - CAT - PSEUDO
                    
                    var date = format(split[1])
                    
                    lastImageData = '<div><h1 align="center">' + split[0] + '</h1><p><b>Ajouté : </b>' + date + '</p><p><b>Description : </b>' + split[2] + '</p><p><b>Catégorie : </b>' + split[3] + '</p><p><b>Par : </b>' + split[4] + '</p></div>';
                    return lastImageData;
                });
        }
        else
        {
            return lastImageData;
        }
      }
    });*/
}

function format(date)
{
    var split = date.split('-');
    return split[2] + '/' + split[1] + '/' + split[0];
}

function search(input)
{
    var exp = input.value;
    if (exp.length >= 2)
    {
        
        $.get(
            'MainController',
             {
                data : 'search',
                exp: exp
             },
            function(data)
            {               
                $("div#mainContainer").html(data);
                makeTooltip();
            });
            
            previousExp = exp.length;
    }
    else
    {
        if (previousExp == 2)
        {
            refreshMain();
        }
        previousExp = exp.length;
    }
}

var previousExp = 0;

function deselectAllCategories()
{
    $("div#left ul li").each(function()
    {
       $(this).removeClass('selected'); 
    });
}

function changeCategorie(clicked)
{
    var catId = clicked.id;
    deselectAllCategories();
    $(clicked).addClass('selected');
    
    $.get(
            'MainController',
             {
                data : 'imgCategorie',
                categorie: catId
             },
            function(data)
            {               
                $("div#mainContainer").html(data);
                makeTooltip();
            });
}

function index()
{
    deselectAllCategories();
    refreshMain();
}

function subscribe()
{
    var login = $("#txt_create_login").val();
    var pass1 = $("#txt_create_password").val();
    var pass2 = $("#txt_create_password2").val();
    var mail = $("#txt_create_mail").val();
    
    if (login === '' || pass1 === '' || pass2 === '' || mail === '')
    {
        showGrowlDiv("error", "Erreur de saisie", "Un champ demeure vide !");
        return null;
    }
    
    if (pass2 != pass1)
    {
        showGrowlDiv("error", "Erreur mot de passe", "Les deux champs ne sont pas identiques !");
        return null;
    }
}