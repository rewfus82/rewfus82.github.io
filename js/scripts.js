/* =====================================================================
   Ryan McLaughlin — Portfolio
   Vanilla JS: nav state, mobile menu, scroll-spy, scroll reveal.
   ===================================================================== */

(() => {
  "use strict";

  /* ---- Rotating hero tagline (random on each load) ---- */
  const HERO_LINES = [
    // --- serious ---
    `Building production <span class="grad">multi-agent AI systems</span>.`,
    `Software engineer specializing in <span class="grad">LLM orchestration</span> and MCP.`,
    `I make LLM prototypes <span class="grad">reliable enough to deploy</span>.`,
    `Multi-agent orchestration, custom MCP servers, <span class="grad">real observability</span>.`,
    `Full-stack engineer with a focus on <span class="grad">applied AI</span>.`,
    `Five years full-stack, now <span class="grad">deep in production AI</span>.`,
    `I build the <span class="grad">infrastructure behind enterprise AI</span>.`,
    `<span class="grad">Human-in-the-loop</span> AI, designed to be trusted.`,
    `Prototype to production with <span class="grad">LangGraph, MCP, and LangSmith</span>.`,
    `I care more about <span class="grad">reliability</span> than demos.`,
    `Backend depth meets <span class="grad">modern AI tooling</span>.`,
    `Engineering AI that <span class="grad">survives real users</span>.`,
    `I make AI <span class="grad">boringly reliable</span> — the best compliment.`,
    `Career-changer who <span class="grad">bet on AI</span> and meant it.`,
    // --- personal / clever ---
    `Sous-chef turned software engineer — still all about <span class="grad">mise en place</span>.`,
    `Former Games Workshop manager — <span class="grad">orchestrating armies</span> for years.`,
    `Recovering recruiter — now I just <span class="grad">interview AI agents</span>.`,
    `Equally opinionated about <span class="grad">films and frameworks</span>.`,
    `Traded the chef's knife for a <span class="grad">keyboard</span> (fewer burns).`,
    `Building cutting-edge AI from <span class="grad">cornfield-adjacent Iowa</span>.`,
    `Once changed my walls' color with an Arduino. <span class="grad">Standards have evolved.</span>`,
    `Best bugs get fixed on a <span class="grad">trail, no laptop in sight</span>.`,
    `Fifteen years reading résumés — now my code <span class="grad">reads everything else</span>.`,
    `Yes, I named the project after a <span class="grad">cooking term</span>. Obviously.`,
    `Built a <span class="grad">Far Cry map</span> once. Multi-agent systems now. Same energy.`,
    `From <span class="grad">kitchen tickets to Kanban tickets</span>.`,
    `I left hospitality to build <span class="grad">software people enjoy</span>.`,
    `I retired from retail management to <span class="grad">manage stack traces</span>.`,
    `<span class="grad">Hiking trails</span> and decision trees.`,
    `I build AI so I have <span class="grad">more time to cook</span>.`,
    `Iowa-based, <span class="grad">cloud-deployed</span>.`,
    // --- dev / AI humor ---
    `My agents run with a <span class="grad">human-in-the-loop</span> because I don't fully trust them either.`,
    `Turning caffeine into <span class="grad">multi-agent AI systems</span>.`,
    `I read the <span class="grad">LangGraph docs</span> so you don't have to.`,
    `I get LLMs to <span class="grad">stop making things up</span>. (Results may vary.)`,
    `I speak Python, Java, and <span class="grad">passive-aggressive YAML</span>.`,
    `Professionally telling computers to <span class="grad">try again</span>.`,
    `I orchestrate agents like I once <span class="grad">orchestrated a kitchen rush</span>.`,
    `I make robots <span class="grad">argue productively</span>. It's called multi-agent design.`,
    `<span class="grad">Kubernetes</span> by day, cast-iron skillet by night.`,
    `Powered by <span class="grad">espresso and stubbornness</span>.`,
    `I've explained "what's an LLM?" at <span class="grad">three family dinners</span>.`,
    `Prompt engineering is just <span class="grad">management with extra steps</span>.`,
    `My commit messages are <span class="grad">better than my handwriting</span>.`,
    `I containerize everything except my <span class="grad">enthusiasm</span>.`,
    `I debug in the shower. <span class="grad">It's a whole workflow.</span>`,
    `Two monitors, infinite tabs, <span class="grad">one meal plan</span>.`,
    `Allegedly a 10x engineer at <span class="grad">making coffee</span>.`,
    `My spiciest hot takes are about <span class="grad">tabs vs. spaces</span>.`,
    `I've named variables worse than I've <span class="grad">named pets</span>.`,
    // --- meta ---
    `Every tagline here was <span class="grad">written by an AI</span>. Including this one.`,
    `An AI wrote all of these — I just <span class="grad">picked the keepers</span>.`,
    `These rotate because I <span class="grad">couldn't pick just one</span>.`,
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
