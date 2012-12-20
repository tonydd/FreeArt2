/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

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
});


