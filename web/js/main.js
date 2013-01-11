/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ajaxStart($.blockUI).ajaxStop($.unblockUI);

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
            alert(data);
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
   
   
   $("img#login").click( function()
    {
       $( "#div_login" ).dialog( "open" );      
    });
    
    $('h1#hihi').click( function ()
    {
        $( "#div_upload" ).dialog( "open" );     
    })
    
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
            alert(data);
            if (data != "OK")
            {
                $.unblockUI();
                document.getElementById('login').setAttribute('src', "img/ok.png");
                $("#login").click(function()
                {
                   $("div#div_menu").animate({
                        height : '+= 300'
                        },
                        1000
                    );
                });
            }
            else
            {
                $.unblockUI();
                alert("LOGIN FAILED");
            }
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
                alert(data);
            },
            data: formData,
            cache: false,
            contentType: false,
            processData: false
        });
}

function addToPanier(photo)
{
    var div = document.createElement('div');
    var title = document.createElement('h2');
    var content = document.createElement('p');
    var img = document.createElement('img');
    
    title.appendChild(document.createTextNode(photo.id));
    content.appendChild(document.createTextNode("Ajouté avec succès au panier !"));
    img.src = 'img/ok.png';
    $(img).css({'float' : 'left'});
    
    div.appendChild(img);
    div.appendChild(title);
    div.appendChild(content);
    
    
    panier.push(photo.id);
    
    document.getElementById('panierCount').innerHTML = panier.length;
    
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

