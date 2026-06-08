/* =====================================================================
   Ryan McLaughlin — Portfolio
   Vanilla JS: nav state, mobile menu, scroll-spy, scroll reveal.
   ===================================================================== */

(() => {
  "use strict";

  /* ---- Rotating hero tagline (random on each load) ---- */
  const HERO_LINES = [
    `Building production <span class="grad">multi-agent AI systems</span>.`,
    `Multi-agent orchestration, custom MCP servers, and the <span class="grad">infrastructure underneath</span>.`,
    `From <span class="grad">Kafka to LangGraph</span>, I work the whole stack.`,
    `Full-stack engineer, <span class="grad">AI-systems focus</span>.`,
    `I make LLMs do <span class="grad">useful, repeatable work</span>.`,
    `Fifteen years solving people problems — now I <span class="grad">solve them with AI</span>.`,
    `Career-changer turned <span class="grad">AI systems engineer</span>.`,
    `Backend engineer with a serious <span class="grad">AI habit</span>.`,
    `I build <span class="grad">AI you can actually click</span> — try the demo below.`,
    `Turning caffeine into <span class="grad">multi-agent AI systems</span>.`,
    `My agents run with a <span class="grad">human-in-the-loop</span>, because I don't fully trust them either.`,
    `Recovering recruiter — now I just <span class="grad">interview AI agents</span>.`,
    `I read the <span class="grad">LangGraph docs</span> so you don't have to.`,
    `I get LLMs to <span class="grad">stop making things up</span>. (Results may vary.)`,
    `Yes, I built an <span class="grad">entire app to plan my dinners</span>. Worth it.`,
  ];
  const heroTitle = document.getElementById("heroTitle");
  if (heroTitle) {
    heroTitle.innerHTML = HERO_LINES[Math.floor(Math.random() * HERO_LINES.length)];
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
