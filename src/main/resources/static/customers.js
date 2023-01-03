angular.module('market', []).controller('CustomerController', function ($scope, $http) {
    const contextPath = 'http://localhost:8089/market'

    $scope.loadCustomers = function() {
        $http.get(contextPath + '/customers')
            .then(function (response) {
                $scope.customers = response.data;
            });
    };

    $scope.deleteCustomer = function(id) {
        $http.get(contextPath + '/delete_customer/' + id)
            .then(function() {
                $scope.loadCustomers()
                }
            );
    };
    $scope.loadCustomers();
});