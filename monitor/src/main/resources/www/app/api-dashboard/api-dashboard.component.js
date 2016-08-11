"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};
var core_1 = require('@angular/core');
var ApiDashboardComponent = (function () {
    function ApiDashboardComponent() {
        this.totalRequestCount = 10;
    }
    ApiDashboardComponent.prototype.ngOnInit = function () {
    };
    ApiDashboardComponent = __decorate([
        core_1.Component({
            moduleId: module.id,
            selector: 'app-api-dashboard',
            templateUrl: 'api-dashboard.component.html',
            styleUrls: ['api-dashboard.component.css']
        }), 
        __metadata('design:paramtypes', [])
    ], ApiDashboardComponent);
    return ApiDashboardComponent;
}());
exports.ApiDashboardComponent = ApiDashboardComponent;
//# sourceMappingURL=api-dashboard.component.js.map