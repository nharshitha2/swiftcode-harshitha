var app = angular.module('chatApp', ['ngMaterial']);
app.config(function ($mdThemingProvider) {
    $mdThemingProvider.theme('default')
        .primaryPalette('purple')


});
app.controller('chatController', function ($scope) {
    $scope.messages = [
        {
            'sender': 'USER',
            'text': 'Hello'
    },
        {

            'sender': 'BOT',
            'text': 'Hello'
    },
        {

            'sender': 'USER',
            'text': 'How are you'
},
        {

            'sender': 'BOT',
            'text': 'I am fine'
}
]


});