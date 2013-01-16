'use strict';

/* Directives */


angular.module('rappels.directives', ['http-auth-interceptor']).directive('loginDirective', function() {
       return {
         restrict: 'C',
         link: function(scope, elem, attrs) {
           //once Angular is started, remove class:
           elem.removeClass('waiting-for-angular');

           var login = elem.find('#login-holder');
           var main = elem.find('#content');

           login.hide();

           scope.$on('event:auth-loginRequired', function() {
             login.slideDown('slow', function() {
               main.hide();
             });
           });
           scope.$on('event:auth-loginConfirmed', function() {
             main.show();
             login.slideUp();
           });
         }
       }
  });
