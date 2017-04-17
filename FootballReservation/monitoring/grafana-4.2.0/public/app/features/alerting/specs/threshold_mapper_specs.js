/*! grafana - v4.2.0 - 2017-03-22
 * Copyright (c) 2017 Torkel Ödegaard; Licensed Apache-2.0 */

System.register(["test/lib/common","../threshold_mapper"],function(a,b){"use strict";var c,d;b&&b.id;return{setters:[function(a){c=a},function(a){d=a}],execute:function(){c.describe("ThresholdMapper",function(){c.describe("with greater than evaluator",function(){c.it("can mapp query conditions to thresholds",function(){var a={type:"graph",alert:{conditions:[{type:"query",evaluator:{type:"gt",params:[100]}}]}},b=d.ThresholdMapper.alertToGraphThresholds(a);c.expect(b).to.be(!0),c.expect(a.thresholds[0].op).to.be("gt"),c.expect(a.thresholds[0].value).to.be(100)})}),c.describe("with outside range evaluator",function(){c.it("can mapp query conditions to thresholds",function(){var a={type:"graph",alert:{conditions:[{type:"query",evaluator:{type:"outside_range",params:[100,200]}}]}},b=d.ThresholdMapper.alertToGraphThresholds(a);c.expect(b).to.be(!0),c.expect(a.thresholds[0].op).to.be("lt"),c.expect(a.thresholds[0].value).to.be(100),c.expect(a.thresholds[1].op).to.be("gt"),c.expect(a.thresholds[1].value).to.be(200)})}),c.describe("with inside range evaluator",function(){c.it("can mapp query conditions to thresholds",function(){var a={type:"graph",alert:{conditions:[{type:"query",evaluator:{type:"within_range",params:[100,200]}}]}},b=d.ThresholdMapper.alertToGraphThresholds(a);c.expect(b).to.be(!0),c.expect(a.thresholds[0].op).to.be("gt"),c.expect(a.thresholds[0].value).to.be(100),c.expect(a.thresholds[1].op).to.be("lt"),c.expect(a.thresholds[1].value).to.be(200)})})})}}});