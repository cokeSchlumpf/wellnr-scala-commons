/** @constructor */
ScalaJS.c.example_ScalaJSExample$ = (function() {
  ScalaJS.c.java_lang_Object.call(this)
});
ScalaJS.c.example_ScalaJSExample$.prototype = new ScalaJS.inheritable.java_lang_Object();
ScalaJS.c.example_ScalaJSExample$.prototype.constructor = ScalaJS.c.example_ScalaJSExample$;
ScalaJS.c.example_ScalaJSExample$.prototype.main__V = (function() {
  ScalaJS.g["document"]["getElementById"]("scalajsShoutOut")["textContent"] = ScalaJS.modules.shared_SharedMessages().itWorks__T()
});
ScalaJS.c.example_ScalaJSExample$.prototype.square__I__I = (function(x) {
  return (x * x)
});
ScalaJS.c.example_ScalaJSExample$.prototype.main = (function() {
  return this.main__V()
});
ScalaJS.c.example_ScalaJSExample$.prototype.square = (function(arg$1) {
  return this.square__I__I(arg$1)
});
ScalaJS.c.example_ScalaJSExample$.prototype.square__I__ = (function(x) {
  return ScalaJS.bI(this.square__I__I(x))
});
ScalaJS.c.example_ScalaJSExample$.prototype.main__ = (function() {
  return ScalaJS.bV(this.main__V())
});
/** @constructor */
ScalaJS.inheritable.example_ScalaJSExample$ = (function() {
  /*<skip>*/
});
ScalaJS.inheritable.example_ScalaJSExample$.prototype = ScalaJS.c.example_ScalaJSExample$.prototype;
/** @constructor */
ScalaJS.classes.example_ScalaJSExample$ = (function() {
  ScalaJS.c.example_ScalaJSExample$.call(this);
  return this.init___()
});
ScalaJS.classes.example_ScalaJSExample$.prototype = ScalaJS.c.example_ScalaJSExample$.prototype;
ScalaJS.is.example_ScalaJSExample$ = (function(obj) {
  return (!(!((obj && obj.$classData) && obj.$classData.ancestors.example_ScalaJSExample$)))
});
ScalaJS.as.example_ScalaJSExample$ = (function(obj) {
  if ((ScalaJS.is.example_ScalaJSExample$(obj) || (obj === null))) {
    return obj
  } else {
    ScalaJS.throwClassCastException(obj, "example.ScalaJSExample")
  }
});
ScalaJS.isArrayOf.example_ScalaJSExample$ = (function(obj, depth) {
  return (!(!(((obj && obj.$classData) && (obj.$classData.arrayDepth === depth)) && obj.$classData.arrayBase.ancestors.example_ScalaJSExample$)))
});
ScalaJS.asArrayOf.example_ScalaJSExample$ = (function(obj, depth) {
  if ((ScalaJS.isArrayOf.example_ScalaJSExample$(obj, depth) || (obj === null))) {
    return obj
  } else {
    ScalaJS.throwArrayCastException(obj, "Lexample.ScalaJSExample;", depth)
  }
});
ScalaJS.data.example_ScalaJSExample$ = new ScalaJS.ClassTypeData({
  example_ScalaJSExample$: 0
}, false, "example.ScalaJSExample$", ScalaJS.data.java_lang_Object, {
  example_ScalaJSExample$: 1,
  java_lang_Object: 1
});
ScalaJS.c.example_ScalaJSExample$.prototype.$classData = ScalaJS.data.example_ScalaJSExample$;
ScalaJS.moduleInstances.example_ScalaJSExample = undefined;
ScalaJS.modules.example_ScalaJSExample = (function() {
  if ((!ScalaJS.moduleInstances.example_ScalaJSExample)) {
    ScalaJS.moduleInstances.example_ScalaJSExample = new ScalaJS.c.example_ScalaJSExample$().init___()
  };
  return ScalaJS.moduleInstances.example_ScalaJSExample
});

/** @constructor */
ScalaJS.c.shared_SharedMessages$ = (function() {
  ScalaJS.c.java_lang_Object.call(this)
});
ScalaJS.c.shared_SharedMessages$.prototype = new ScalaJS.inheritable.java_lang_Object();
ScalaJS.c.shared_SharedMessages$.prototype.constructor = ScalaJS.c.shared_SharedMessages$;
ScalaJS.c.shared_SharedMessages$.prototype.itWorks__T = (function() {
  return "It works!"
});
ScalaJS.c.shared_SharedMessages$.prototype.itWorks = (function() {
  return this.itWorks__T()
});
ScalaJS.c.shared_SharedMessages$.prototype.itWorks__ = (function() {
  return this.itWorks__T()
});
/** @constructor */
ScalaJS.inheritable.shared_SharedMessages$ = (function() {
  /*<skip>*/
});
ScalaJS.inheritable.shared_SharedMessages$.prototype = ScalaJS.c.shared_SharedMessages$.prototype;
/** @constructor */
ScalaJS.classes.shared_SharedMessages$ = (function() {
  ScalaJS.c.shared_SharedMessages$.call(this);
  return this.init___()
});
ScalaJS.classes.shared_SharedMessages$.prototype = ScalaJS.c.shared_SharedMessages$.prototype;
ScalaJS.is.shared_SharedMessages$ = (function(obj) {
  return (!(!((obj && obj.$classData) && obj.$classData.ancestors.shared_SharedMessages$)))
});
ScalaJS.as.shared_SharedMessages$ = (function(obj) {
  if ((ScalaJS.is.shared_SharedMessages$(obj) || (obj === null))) {
    return obj
  } else {
    ScalaJS.throwClassCastException(obj, "shared.SharedMessages")
  }
});
ScalaJS.isArrayOf.shared_SharedMessages$ = (function(obj, depth) {
  return (!(!(((obj && obj.$classData) && (obj.$classData.arrayDepth === depth)) && obj.$classData.arrayBase.ancestors.shared_SharedMessages$)))
});
ScalaJS.asArrayOf.shared_SharedMessages$ = (function(obj, depth) {
  if ((ScalaJS.isArrayOf.shared_SharedMessages$(obj, depth) || (obj === null))) {
    return obj
  } else {
    ScalaJS.throwArrayCastException(obj, "Lshared.SharedMessages;", depth)
  }
});
ScalaJS.data.shared_SharedMessages$ = new ScalaJS.ClassTypeData({
  shared_SharedMessages$: 0
}, false, "shared.SharedMessages$", ScalaJS.data.java_lang_Object, {
  shared_SharedMessages$: 1,
  java_lang_Object: 1
});
ScalaJS.c.shared_SharedMessages$.prototype.$classData = ScalaJS.data.shared_SharedMessages$;
ScalaJS.moduleInstances.shared_SharedMessages = undefined;
ScalaJS.modules.shared_SharedMessages = (function() {
  if ((!ScalaJS.moduleInstances.shared_SharedMessages)) {
    ScalaJS.moduleInstances.shared_SharedMessages = new ScalaJS.c.shared_SharedMessages$().init___()
  };
  return ScalaJS.moduleInstances.shared_SharedMessages
});

ScalaJS.modules.example_ScalaJSExample().main();
//@ sourceMappingURL=scalajs-example.js.map
