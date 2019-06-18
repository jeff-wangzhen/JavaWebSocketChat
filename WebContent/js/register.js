var sendMessages = {};
var validNum = 2;
var $timeBox = $("#time_box");
var $verifyYz = $("#verifyYz");
var flagSendVerification = false;
document.getElementById("particles-js").style.display = "block";
(function($) {
    var h, timerC = 60, opt;
    var j = function(a) {
        a = $.extend(require.defaults, a || {});
        opt = a;
        return (new require())._init(a);
    };
    function require(f) {
        var g = {
            email: /^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\.[a-zA-Z0-9-]+)*\.[a-zA-Z0-9]+$/,
            company: /^[\u4E00-\u9FA5a-zA-Z][\u4E00-\u9FA5a-zA-Z0-9\s-,-.]*$/,
            uname: /^[\u4E00-\u9FA5a-zA-Z][\u4E00-\u9FA5a-zA-Z0-9_]*$/,
            zh: /^[\u4e00-\u9fa5]+$/,
            card: /^((1[1-5])|(2[1-3])|(3[1-7])|(4[1-6])|(5[0-4])|(6[1-5])|71|(8[12])|91)\d{4}(((((19|20)((\d{2}(0[13-9]|1[012])(0[1-9]|[12]\d|30))|(\d{2}(0[13578]|1[02])31)|(\d{2}02(0[1-9]|1\d|2[0-8]))|(([13579][26]|[2468][048]|0[48])0229)))|20000229)\d{3}(\d|X|x))|(((\d{2}(0[13-9]|1[012])(0[1-9]|[12]\d|30))|(\d{2}(0[13578]|1[02])31)|(\d{2}02(0[1-9]|1\d|2[0-8]))|(([13579][26]|[2468][048]|0[48])0229))\d{3}))$/,
            int: /^[0-9]*$/,
            s: ''
        };
        this.rules = {
            isNonEmpty: function(a, b) {
                b = b || " ";
                if (!a.length)
                    return b
            },
            hasEmail: function(a, b, c) {},
            minLength: function(a, b, c) {
                c = c || " ";
                if (a.length < b)
                    return c
            },
            maxLength: function(a, b, c) {
                c = c || " ";
                if (a.length > b)
                    return c
            },
            isRepeat: function(a, b, c) {
                c = c || " ";
                if (a !== $("#" + b).val())
                    return c
            },
            between: function(a, b, c) {
                c = c || " ";
                var d = parseInt(b.split('-')[0]);
                var e = parseInt(b.split('-')[1]);
                if (a.length < d || a.length > e)
                    return c
            },
            level: function(a, b, c) {
                c = c || " ";
                var r = j.pwdStrong(a);
                if (b > 4)
                    b = 3;
                if (r < b)
                    return c
            },
            isEmail: function(a, b) {
                b = b || " ";
                if (!g.email.test(a))
                    return b
            },
            isCompany: function(a, b) {
                b = b || " ";
                if (!g.company.test(a))
                    return b
            },
            isInt: function(a, b) {
                b = b || " ";
                if (!g.int.test(a))
                    return b
            },
            isUname: function(a, b) {
                b = b || " ";
                if (!g.uname.test(a))
                    return b
            },
            isZh: function(a, b) {
                b = b || " ";
                if (!g.zh.test(a))
                    return b
            },
            isCard: function(a, b) {
                b = b || " ";
                if (!g.card.test(a))
                    return b
            },
            isChecked: function(c, d, e) {
                d = d || " ";
                var a = $(e).find('input:checked').length
                  , b = $(e).find('.on').length;
                if (!a && !b)
                    return d;
            }
        }
    }
    ;require.prototype = {
        _init: function(b) {
            this.config = b;
            this.getInputs = $('#' + b.formId).find('.required:visible');
            var c = false;
            var d = this;
            if (b.code) {
                $verifyYz.click(function() {
                    if (sendMessages["email"] && !flagSendVerification) {
                        flagSendVerification = true;
                        $verifyYz.text("正在发送");
                        $.post("SendMail", sendMessages, function(data) {
                            var JSONData = JSON.parse(data);
                            if (JSONData.error) {
                                alert(JSONData.error);
                                delete sendMessages["email"];
                            } else {
                                d._sendVerify();
                            }
                            $verifyYz.text("发送验证码");
                            flagSendVerification = false;
                        });
                    } else {}
                })
            }
            $('body').on({
                blur: function(a) {
                    d.formValidator($(this));
                    if (b.email && $(this).attr("id") === "email")
                        d._change($(this));
                    b.onBlur ? b.onBlur($(this)) : ''
                },
                focus: function(a) {
                    b.onFocus ? b.onFocus($(this)) : $(this).parent().find("label.focus").not(".valid").removeClass("hide").siblings(".valid").addClass("hide") && $(this).parent().find(".blank").addClass("hide") && $(this).parent().find(".close").addClass("hide")
                },
                keyup: function(a) {
                    if (b.email && $(this).attr("id") === "email")
                        d._change($(this))
                },
                change: function(a) {
                    b.onChange ? b.onChange($(this)) : ''
                }
            }, "#" + b.formId + " .required:visible");
            $('body').on("click", ".close", function() {
                var p = $(this).parent()
                  , input = p.find("input");
                input.val("").focus()
            })
        },
        formValidator: function(a) {
            var b = a.attr('data-valid');
            if (b === undefined)
                return false;
            var c = b.split('||');
            var d = a.attr('data-error');
            if (d === undefined)
                d = "";
            var e = d.split("||");
            var f = [];
            for (var i = 0; i < c.length; i++) {
                f.push({
                    strategy: c[i],
                    errorMsg: e[i]
                })
            }
            ;return this._add(a, f)
        },
        _add: function(a, b) {
            var d = this;
            for (var i = 0, rule; (rule = b[i++]); ) {
                var e = rule.strategy.split(':');
                var f = rule.errorMsg;
                var g = e.shift();
                e.unshift(a.val());
                e.push(f);
                e.push(a);
                var c = d.rules[g].apply(a, e);
                sendMessages[a.attr("id")] = e[0];
                if (c) {
                    opt.resultTips ? opt.resultTips(a, false, c) : j._resultTips(a, false, c);
                    return false
                }
            }
            opt.successTip ? (opt.resultTips ? opt.resultTips(a, true) : j._resultTips(a, true)) : j._clearTips(a);
            return true
        },
        _sendVerify: function() {
            var a = this;
            $verifyYz.text("发送验证码").hide();
            $timeBox.text("10 s后可重发").show();
            if (timerC === 0) {
                clearTimeout(h);
                timerC = 60;
                var reg = new RegExp("^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$");
                $verifyYz.show();
                $timeBox.hide()
                return;
            }
            $timeBox.text(timerC + " s后可重发");
            timerC--;
            h = setTimeout(function() {
                a._sendVerify()
            }, 1000)
        },
        _change: function(a) {
            var b = this;
            if (a.val().length !== 11) {
                $verifyYz.hide();
                $timeBox.show();
                if (timerC === 60)
                    $timeBox.text("发送验证码");
                $("#verifyNo").val("");
                this.config.clearTips ? this.config.clearTips($("#verifyNo")) : j._clearTips($("#verifyNo"));
                return
            }
            var c = /^1([^01269])\d{9}$/;
            if (!c.test(a.val()))
                return false;
            if (timerC === 60) {
                $verifyYz.show();
                $timeBox.hide()
            } else {
                $verifyYz.hide();
                $timeBox.show()
            }
        }
    };
    j._click = function(c) {
        c = c || opt.formId;
        var d = $("#" + c).find('.required:visible')
          , self = this
          , result = true
          , t = new require()
          , r = [];
        $.each(d, function(a, b) {
            result = t.formValidator($(b));
            if (result)
                r.push(result)
        });
        if (d.length !== r.length)
            result = false;
        return result
    }
    ;
    j._clearTips = function(a) {
        a.parent().find(".blank").addClass("hide");
        a.parent().find(".valid").addClass("hide");
        a.removeClass("v_error");
        validNum++;
    }
    ;
    j._resultTips = function(a, b, c) {
        a.parent().find("label.focus").not(".valid").addClass("hide").siblings(".focus").removeClass("hide");
        a.parent().find(".close").addClass("hide");
        a.removeClass("v_error");
        validNum++;
        if ("rePassword" === a.attr("id")) {
            a.parents(".aui-register-form-item")[0].style.marginBottom = "20px";
        }
        c = c || "";
        if (c.length > 21)
            c = "<span>" + c + "</span>";
        var o = a.parent().find("label.valid");
        if (!b) {
            delete sendMessages[a.attr("id")];
            o.addClass("error");
            a.addClass("v_error");
            validNum--;
            if ($.trim(a.val()).length > 0)
                a.parent().find(".close").removeClass("hide")
        } else {
            a.parent().find(".blank").removeClass("hide")
        }
        o.text("").append(c)
        o.css("height", "0");
        if (c !== "")
            o.css("height", "35px");
    }
    ;
    j.textChineseLength = function(a) {
        var b = /[\u4E00-\u9FA5]|[\u3001-\u3002]|[\uFF1A-\uFF1F]|[\u300A-\u300F]|[\u3010-\u3015]|[\u2013-\u201D]|[\uFF01-\uFF0E]|[\u3008-\u3009]|[\u2026]|[\uffe5]/g;
        if (b.test(a))
            return a.match(b).length;
        else
            return 0
    }
    ;
    j.pwdStrong = function(a) {
        var b = 0;
        if (a.match(/[a-z]/g)) {
            b++
        }
        if (a.match(/[A-Z]/g)) {
            b++
        }
        if (a.match(/[0-9]/g)) {
            b++
        }
        if (a.match(/(.[^a-z0-9A-Z])/g)) {
            b++
        }
        if (b > 4) {
            b = 4
        }
        if (b === 0)
            return false;
        return b
    }
    ;
    require.defaults = {
        formId: 'verifyCheck',
        onBlur: null,
        onFocus: null,
        onChange: null,
        successTip: true,
        resultTips: null,
        clearTips: null,
        code: true,
        email: false
    };
    window.verifyCheck = $.verifyCheck = j
}
)(jQuery);
(function($) {
    var f;
    var g = function() {
        return (new require())._init()
    };
    function require(a) {}
    ;require.prototype = {
        _init: function() {
            var b = this;
            $('body').on({
                click: function(a) {
                    b._click($(this))
                }
            }, ".showpwd:visible")
        },
        _click: function(a) {
            var c = a.attr('data-eye');
            if (c === undefined)
                return false;
            var d = $("#" + c)
              , cls = !d.attr("class") ? "" : d.attr("class")
              , value = !d.val() ? "" : d.val()
              , type = d.attr("type") === "password" ? "text" : "password"
              , b = d.parent().find("b.placeTextB")
              , isB = b.length === 0 ? false : true;
            var s = d.attr("name") ? " name='" + d.attr("name") + "'" : "";
            s += d.attr("data-valid") ? " data-valid='" + d.attr("data-valid") + "'" : "";
            s += d.attr("data-error") ? " data-error='" + d.attr("data-error") + "'" : "";
            s += d.attr("placeholder") ? " placeholder='" + d.attr("placeholder") + "'" : "";
            var e = '<input readonly type="' + type + '" class="' + cls + '" value="' + value + '" id="' + c + '"' + s + ' />';
            if (type === "text") {
                if (isB)
                    b.hide();
                d.parent().find(".icon-close.close").addClass("hide");
                d.removeAttr("id").hide();
                d.after(e);
                a.addClass("hidepwd")
            } else {
                d.prev("input").attr("id", c).val(value).show();
                if (isB && $.trim(value) === "") {
                    d.prev("input").hide();
                    b.show()
                }
                d.remove();
                a.removeClass("hidepwd")
            }
            ;$('body').on("click", "#" + c, function() {
                $(this).parent().find(".hidepwd").click();
                if (isB && $.trim($(this).val()) === "") {
                    d.show();
                    b.hide()
                }
                d.focus()
            })
        }
    };
    require.defaults = {};
    window.togglePwd = $.togglePwd = g
}
)(jQuery);
(function($) {
    var b, timerC, opt;
    var d = function(a) {
        a = $.extend(require.defaults, a || {});
        opt = a;
        d._clear();
        return (new require())._init()
    };
    function require(a) {}
    ;require.prototype = {
        _init: function() {
            timerC = opt.maxTime;
            this._sendVerify()
        },
        _sendVerify: function() {
            var a = this;
            if (timerC === 0) {
                d._clear();
                opt.after();
                timerC = opt.maxTime;
                return
            }
            timerC--;
            opt.ing(timerC);
            b = setTimeout(function() {
                a._sendVerify()
            }, 1000)
        }
    };
    d._clear = function() {
        clearTimeout(b)
    }
    ;
    require.defaults = {
        maxTime: 60,
        minTime: 0,
        ing: function(c) {},
        after: function() {}
    };
    window.countdown = $.countdown = d
}
)(jQuery);
(function($) {
    togglePwd();
    verifyCheck();
    $('body').on("blur", "#password", function() {
    	if($("#rePassword").val()!=""){
    		  $("#rePassword").blur();
    	}     
    });    
    $('body').on("keyup", "#password", function() {
        var t = $(this).val()
          , o = $(this).parent().find(".strength");
        if (t.length >= 6) {
            o.show();
            var l = verifyCheck.pwdStrong(t);
            o.find("b i").removeClass("on");
            for (var i = 0; i < l; i++) {
                o.find("b i").eq(i).addClass("on")
            }
        } else {
            o.hide();
        }
    });
    $("#aui-btn-reg").click(function() {
        "use strict";
        sendMessages["portrait"] = $("#avatar").eq(0).attr("src");
        sendMessages["gender"] = $("#gender").prop("checked") ? "男" : "女";
        sendMessages["isChecked"] = String($("#agree").prop("checked"));
        if (validNum < 5)
            return false;
        if (sendMessages.length < 9)
            return false;
        if (sendMessages["isChecked"] !== "true")
            return false;
        $.post("CheckRegister", sendMessages, function(data) {
            var JSONData = JSON.parse(data);
            console.log(JSONData);
            if (JSONData.error) {
                alert(JSONData.error);
            } else if (JSONData.url)
                window.location.href = JSONData.url;
        });
    });
}
)(jQuery);
