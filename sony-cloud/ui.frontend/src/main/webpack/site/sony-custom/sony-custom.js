!function(){var e={834:function(e,t,n){"use strict";n(834),n(356)},356:function(){!function(){"use strict";var e='[data-cmp-is="helloworld"]',t='[data-cmp-hook-helloworld="property"]',n='[data-cmp-hook-helloworld="model"]';function o(e){e&&e.element&&function(e){e.element.removeAttribute("data-cmp-is");var o=e.element.querySelectorAll(t);o=1==o.length?o[0].textContent:null;var r=e.element.querySelectorAll(n);r=1==r.length?r[0].textContent:null,console&&console.log&&console.log("HelloWorld component JavaScript example","\nText property:\n",o,"\nModel message:\n",r)}(e)}function r(){for(var t=document.querySelectorAll(e),n=0;n<t.length;n++)new o({element:t[n]});var r=window.MutationObserver||window.WebKitMutationObserver||window.MozMutationObserver,l=document.querySelector("body");new r((function(t){t.forEach((function(t){var n=[].slice.call(t.addedNodes);n.length>0&&n.forEach((function(t){t.querySelectorAll&&[].slice.call(t.querySelectorAll(e)).forEach((function(e){new o({element:e})}))}))}))})).observe(l,{subtree:!0,childList:!0,characterData:!0})}"loading"!==document.readyState?r():document.addEventListener("DOMContentLoaded",r)}()}},t={};function n(o){var r=t[o];if(void 0!==r)return r.exports;var l=t[o]={exports:{}};return e[o](l,l.exports,n),l.exports}n.n=function(e){var t=e&&e.__esModule?function(){return e.default}:function(){return e};return n.d(t,{a:t}),t},n.d=function(e,t){for(var o in t)n.o(t,o)&&!n.o(e,o)&&Object.defineProperty(e,o,{enumerable:!0,get:t[o]})},n.o=function(e,t){return Object.prototype.hasOwnProperty.call(e,t)};n(834)}();
// footnote js


document.querySelectorAll("[data-footnotes]").forEach(component => {

  const button = component.querySelector("[data-toggle]");
  const body = component.querySelector(".Footnotes__BodyContainer");

  button.addEventListener("click", () => {
    const isOpen = button.getAttribute("aria-expanded") === "true";

    button.setAttribute("aria-expanded", !isOpen);
    component.classList.toggle("-isExpanded");

    body.style.display = isOpen ? "none" : "block";
  });

  // Popup close
  const popup = component.querySelector("[data-popup]");
  const closeBtn = component.querySelector(".FootnotePopup__CloseButton");

  if (closeBtn) {
    closeBtn.addEventListener("click", () => {
      popup.classList.remove("active");
    });
  }

});


document.addEventListener("DOMContentLoaded", function () {

    const slider = document.querySelector(".PromotionTilesItem__Wrapper");
    const slides = document.querySelectorAll(".PromotionTilesItem__Slide");
    const next = document.querySelector(".PromotionTilesItem__NextButton");
    const prev = document.querySelector(".PromotionTilesItem__PrevButton");

    if (!slider || slides.length === 0) return;

    let index = 0;

    function getVisibleSlides() {
        return window.innerWidth < 768 ? 1 : 4;
    }

    function updateSlider() {
        const slideWidth = slides[0].offsetWidth;
        slider.style.transform = `translateX(-${index * slideWidth}px)`;
    }

    next.addEventListener("click", () => {
        if (index < slides.length - getVisibleSlides()) {
            index++;
            updateSlider();
        }
    });

    prev.addEventListener("click", () => {
        if (index > 0) {
            index--;
            updateSlider();
        }
    });

    window.addEventListener("resize", updateSlider);

});