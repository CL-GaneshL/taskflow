'use strict';
/** 
 * controller for User Profile Example
 */
app.controller(
        'profileCtrl',
        [
            'pageSrvc',
            function (pageSrvc) {

                // the Profile page is being displayed
                pageSrvc.setProfilePage(true);
            }
        ]

        );

