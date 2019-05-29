/**
 * Created by 吴志杨 on 5/29/2019.
 */
var actionApp = angular.module('actionApp', ['ngRoute']);
actionApp.config(['$routeProvider', function ($routeProvider) {
    $routeProvider.when('/oper', {
        controller: 'View1Controller',
        templateUrl: 'view1'
    }).when('/directive', {
        controller: 'View2Controller',
        templateUrl: 'view2'
    });
}]);

actionApp.controller('View1Controller', ['$rootScope', '$scope', '$http',
    function ($rootScope, $scope, $http) {
        //$on监听$viewContentLoaded事件，可以可以在页面加载完成后进行一些操作。
        $scope.$on('$viewContentLoaded', function () {
            console.log('页面加载完成');
        });

        $scope.search = function () {
            var personName = $scope.personName;
            $http.get('search', {
                params: {personName: personName}
            }).success(function (data) {
                $scope.person = data;
            });
        }
    }]);

actionApp.controller('View2Controller', ['$rootScope', '$scope',
    function ($rootScope, $scope) {
        $scope.$on('$viewContentLoaded', function () {
            console.log('页面加载完成');
        });
    }])

actionApp.directive('datePicker', function () {
    return {
        restrict: 'AC',
        link: function (scope, elem, attrs) {
            elem.datepicker();
        }
    }
});
