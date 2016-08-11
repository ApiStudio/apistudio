/* tslint:disable:no-unused-variable */
"use strict";
var testing_1 = require('@angular/core/testing');
var app_component_1 = require('./app.component');
describe('App: ApistudioMonitor', function () {
    beforeEach(function () {
        testing_1.addProviders([app_component_1.AppComponent]);
    });
    it('should create the app', testing_1.inject([app_component_1.AppComponent], function (app) {
        expect(app).toBeTruthy();
    }));
    it('should have as title \'app works!\'', testing_1.inject([app_component_1.AppComponent], function (app) {
        expect(app.title).toEqual('app works!');
    }));
});
//# sourceMappingURL=app.component.spec.js.map