/* =====================================================================
   Ryan McLaughlin — Portfolio
   Vanilla JS: nav state, mobile menu, scroll-spy, scroll reveal.
   ===================================================================== */

(() => {
  "use strict";

  /* ---- Rotating hero tagline (random on each load) ---- */
  const HERO_LINES = [
    `Sous-chef turned software engineer — still all about <span class="grad">mise en place</span>.`,
    `Former Games Workshop manager — I've been <span class="grad">orchestrating armies</span> for years.`,
    `Recovering recruiter — now I just <span class="grad">interview AI agents</span>.`,
    `Equally opinionated about <span class="grad">films and frameworks</span>.`,
    `Traded the chef's knife for a <span class="grad">keyboard</span> (fewer burns).`,
    `Building cutting-edge AI from <span class="grad">cornfield-adjacent Iowa</span>.`,
    `Once made my walls change color with an Arduino. <span class="grad">Standards have evolved.</span>`,
    `Best bugs get fixed on a <span class="grad">trail, no laptop in sight</span>.`,
    `Fifteen years reading résumés — now I write code that <span class="grad">reads everything else</span>.`,
    `My agents run with a <span class="grad">human-in-the-loop</span> because I don't fully trust them either.`,
    `Turning caffeine into <span class="grad">multi-agent AI systems</span>.`,
    `Yes, I named the project after a <span class="grad">cooking term</span>. Obviously.`,
    `I read the <span class="grad">LangGraph docs</span> so you don't have to.`,
    `Built a <span class="grad">Far Cry map</span> once. Multi-agent systems now. Same energy.`,
    `I get LLMs to <span class="grad">stop making things up</span>. (Results may vary.)`,
  ];
  const heroLine = document.getElementById("heroLine");
  if (heroLine) {
    const reduceMotion = window.matchMedia("(prefers-reduced-motion: reduce)").matches;
    let idx = Math.floor(Math.random() * HERO_LINES.length);
    heroLine.innerHTML = HERO_LINES[idx];

    if (!reduceMotion && typeof heroLine.animate === "function") {
      const OUT = [
        { opacity: 1, transform: "translateX(0)" },
        { opacity: 0, transform: "translateX(28px)" },
      ];
      const IN = [
        { opacity: 0, transform: "translateX(-28px)" },
        { opacity: 1, transform: "translateX(0)" },
      ];
      setInterval(() => {
        if (document.hidden) return; // don't churn in a background tab
        const out = heroLine.animate(OUT, { duration: 260, easing: "cubic-bezier(.4,0,1,1)" });
        out.onfinish = () => {
          heroLine.style.opacity = "0"; // hold hidden through the swap
          idx = (idx + 1) % HERO_LINES.length;
          heroLine.innerHTML = HERO_LINES[idx];
          const enter = heroLine.animate(IN, { duration: 340, easing: "cubic-bezier(0,0,.2,1)" });
          enter.onfinish = () => { heroLine.style.opacity = ""; };
        };
      }, 5000);
    }
  }

  const nav = document.getElementById("nav");
  const toggle = document.getElementById("navToggle");
  const links = document.getElementById("navLinks");
  const navAnchors = Array.from(
    links.querySelectorAll('a[href^="#"]')
  );

  /* ---- Sticky nav background on scroll ---- */
  const onScroll = () => {
    nav.classList.toggle("is-scrolled", window.scrollY > 24);
  };
  onScroll();
  window.addEventListener("scroll", onScroll, { passive: true });

  /* ---- Mobile menu toggle ---- */
  const closeMenu = () => {
    links.classList.remove("is-open");
    toggle.setAttribute("aria-expanded", "false");
  };
  toggle.addEventListener("click", () => {
    const open = links.classList.toggle("is-open");
    toggle.setAttribute("aria-expanded", String(open));
  });
  links.addEventListener("click", (e) => {
    if (e.target.matches("a")) closeMenu();
  });
  document.addEventListener("keydown", (e) => {
    if (e.key === "Escape") closeMenu();
  });

  /* ---- Scroll-spy: highlight active nav link ---- */
  const sections = navAnchors
    .map((a) => document.querySelector(a.getAttribute("href")))
    .filter(Boolean);

  const spy = new IntersectionObserver(
    (entries) => {
      entries.forEach((entry) => {
        if (!entry.isIntersecting) return;
        const id = entry.target.id;
        navAnchors.forEach((a) =>
          a.classList.toggle("is-active", a.getAttribute("href") === `#${id}`)
        );
      });
    },
    { rootMargin: "-45% 0px -50% 0px" }
  );
  sections.forEach((s) => spy.observe(s));

  /* ---- Scroll reveal ---- */
  const revealEls = document.querySelectorAll(".reveal");
  if (!window.matchMedia("(prefers-reduced-motion: reduce)").matches) {
    const revealer = new IntersectionObserver(
      (entries, obs) => {
        entries.forEach((entry, i) => {
          if (!entry.isIntersecting) return;
          // small stagger for groups entering together
          entry.target.style.transitionDelay = `${Math.min(i * 60, 240)}ms`;
          entry.target.classList.add("is-visible");
          obs.unobserve(entry.target);
        });
      },
      { threshold: 0.12 }
    );
    revealEls.forEach((el) => revealer.observe(el));
  } else {
    revealEls.forEach((el) => el.classList.add("is-visible"));
  }

  /* ---- Footer year ---- */
  const year = document.getElementById("year");
  if (year) year.textContent = new Date().getFullYear();
})();
