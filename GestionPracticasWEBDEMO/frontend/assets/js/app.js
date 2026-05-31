import { api, orderByState } from './api.js';

const session = JSON.parse(localStorage.getItem('GPA_SESSION') || 'null');
if (!session) location.href = 'index.html';

const state = {
  route: 'dashboard',
  activeTab: 'TODOS',
  currentEntity: null,
  editingId: null,
  cache: {}
};

const roleMenus = {
  DIRECTOR: [
    ['dashboard', 'Dashboard'], ['usuarios', 'Usuarios'], ['practicas', 'Prácticas'], ['cursos', 'Cursos'], ['instituciones', 'Instituciones'], ['reportes', 'Reportes'], ['chat', 'Chat interno']
  ],
  COORDINADOR: [
    ['dashboard', 'Dashboard'], ['grupos', 'Grupos de práctica'], ['asignaciones', 'Asignación docente'], ['usuarios', 'Estudiantes'], ['instituciones', 'Instituciones'], ['actividades', 'Control de horas'], ['chat', 'Chat interno']
  ],
  DOCENTE: [
    ['dashboard', 'Dashboard'], ['actividades', 'Revisión de actividades'], ['rubricas', 'Rúbricas'], ['preguntas', 'Preguntas'], ['evaluaciones', 'Evaluaciones'], ['chat', 'Chat interno']
  ],
  ESTUDIANTE: [
    ['dashboard', 'Mi paso a paso'], ['matricula', 'Mi matrícula'], ['actividades', 'Actividades'], ['documentos', 'Documentos'], ['notas', 'Notas'], ['inquietudes', 'Inquietudes'], ['chat', 'Chat interno']
  ],
  INSTITUCION: [
    ['dashboard', 'Dashboard institución'], ['convenios', 'Convenio'], ['practicantes', 'Practicantes'], ['actividades', 'Aprobación de horas'], ['compatibilidad', 'Compatibilidad'], ['certificados', 'Certificados'], ['chat', 'Chat interno']
  ]
};

const entityConfig = {
  usuarios: {
    title: 'Usuarios', entity: 'usuarios', id: 'id_usuario', description: 'Gestión de usuarios por rol con activar, inactivar y eliminación lógica.',
    columns: ['nombre', 'apellido', 'correo_electronico', 'rol', 'estado'],
    fields: [
      ['nombre', 'Nombre', 'text'], ['apellido', 'Apellido', 'text'], ['correo_electronico', 'Correo', 'email'], ['telefono', 'Teléfono', 'text'],
      ['rol', 'Rol', 'select', ['DIRECTOR', 'COORDINADOR', 'DOCENTE', 'ESTUDIANTE', 'INSTITUCION']], ['estado', 'Estado', 'select', estados()]
    ]
  },
  practicas: {
    title: 'Prácticas', entity: 'practicas', id: 'id_practica', description: 'Administración de prácticas académicas y horas reglamentarias.',
    columns: ['nombre', 'tipo_modalidad', 'horas_reglamentarias', 'fecha_inicio', 'fecha_fin', 'estado'],
    fields: [['nombre', 'Nombre', 'text'], ['tipo_modalidad', 'Modalidad', 'select', ['Presencial', 'Virtual', 'Híbrida']], ['horas_reglamentarias', 'Horas reglamentarias', 'number'], ['fecha_inicio', 'Fecha inicio', 'date'], ['fecha_fin', 'Fecha fin', 'date'], ['estado', 'Estado', 'select', estados()], ['descripcion', 'Descripción', 'textarea']]
  },
  cursos: {
    title: 'Cursos', entity: 'cursos', id: 'id_curso', description: 'CRUD de cursos con activación e inactivación conectado a base de datos.',
    columns: ['nombre', 'codigo', 'semestre', 'estado'],
    fields: [['nombre', 'Nombre del curso', 'text'], ['codigo', 'Código', 'text'], ['semestre', 'Semestre', 'text'], ['estado', 'Estado', 'select', estados()]]
  },
  instituciones: {
    title: 'Instituciones receptoras', entity: 'instituciones', id: 'id_institucion', description: 'Gestión de instituciones, convenio, historial, logo, estado del servicio y contacto.',
    columns: ['nombre', 'nit', 'ciudad', 'representante', 'email', 'estado'],
    fields: [['nombre', 'Nombre', 'text'], ['nit', 'NIT', 'text'], ['ciudad', 'Ciudad', 'text'], ['representante', 'Representante', 'text'], ['email', 'Email', 'email'], ['telefono', 'Teléfono', 'text'], ['sitio_web', 'Sitio web', 'url'], ['estado', 'Estado', 'select', estados()], ['direccion', 'Dirección', 'textarea']]
  },
  grupos: {
    title: 'Grupos de práctica', entity: 'grupos', id: 'id_grupo', description: 'CRUD de grupos con institución, docente, cupo máximo y estado.',
    columns: ['nombre_grupo', 'periodo_academico', 'cupo_maximo', 'id_docente', 'id_institucion', 'estado'],
    fields: [['nombre_grupo', 'Nombre del grupo', 'text'], ['periodo_academico', 'Período', 'text'], ['cupo_maximo', 'Cupo máximo', 'number'], ['id_docente', 'ID docente', 'number'], ['id_institucion', 'ID institución', 'number'], ['estado', 'Estado', 'select', estados()]]
  },
  asignaciones: {
    title: 'Asignación docente', entity: 'asignaciones', id: 'id_asignacion_docente', description: 'Vinculación formal de docente asesor a grupo de práctica.',
    columns: ['id_usuario', 'id_grupo', 'fecha_asignacion', 'estado'],
    fields: [['id_usuario', 'ID docente', 'number'], ['id_grupo', 'ID grupo', 'number'], ['fecha_asignacion', 'Fecha de asignación', 'date'], ['estado', 'Estado', 'select', estados()]]
  },
  actividades: {
    title: 'Actividades y control de horas', entity: 'actividades', id: 'id_actividad', description: 'Bitácora de actividades, horas, comentarios e historial de revisión.',
    columns: ['fecha_actividad', 'tipo_actividad', 'horas', 'descripcion', 'estado'],
    fields: [['fecha_actividad', 'Fecha', 'date'], ['tipo_actividad', 'Tipo de actividad', 'text'], ['horas', 'Horas', 'number'], ['estado', 'Estado', 'select', estados().concat('REQUIERE_REVISION')], ['descripcion', 'Descripción', 'textarea'], ['comentarios', 'Comentarios', 'textarea']]
  },
  convenios: {
    title: 'Convenios', entity: 'convenios', id: 'id_convenio', description: 'Consulta y administración de convenios con instituciones receptoras.',
    columns: ['numero_convenio', 'id_institucion', 'fecha_inicio', 'fecha_fin', 'estado'],
    fields: [['numero_convenio', 'Número de convenio', 'text'], ['id_institucion', 'ID institución', 'number'], ['fecha_inicio', 'Fecha inicio', 'date'], ['fecha_fin', 'Fecha fin', 'date'], ['estado', 'Estado', 'select', estados()], ['objeto', 'Objeto del convenio', 'textarea']]
  },
  compatibilidad: {
    title: 'Compatibilidad institución-estudiante', entity: 'compatibilidad', id: 'id_compatibilidad', description: 'Recomendación de instituciones según intereses, resultados y perfil del estudiante.',
    columns: ['id_usuario', 'id_institucion', 'porcentaje', 'criterios'],
    fields: [['id_usuario', 'ID estudiante', 'number'], ['id_institucion', 'ID institución', 'number'], ['porcentaje', 'Porcentaje', 'number'], ['criterios', 'Criterios', 'textarea']]
  }
};

function estados() { return ['ACTIVO', 'PENDIENTE', 'APROBADO', 'INACTIVO', 'REPROBADO']; }
const $ = (sel) => document.querySelector(sel);
const $$ = (sel) => Array.from(document.querySelectorAll(sel));
const content = $('#content');
const title = $('#pageTitle');
const subtitle = $('#pageSubtitle');

init();

function init() {
  setupShell();
  renderMenu();
  bindGlobalActions();
  navigate('dashboard');
}

function setupShell() {
  const fullName = `${session.nombre || ''} ${session.apellido || ''}`.trim();
  $('#sidebarName').textContent = fullName || 'Usuario';
  $('#sidebarRole').textContent = session.rol;
  const initial = (session.nombre || session.rol || 'U').charAt(0).toUpperCase();
  $('#avatar').textContent = initial;
  if (session.fotoPerfilUrl || session.foto_perfil_url) {
    $('#avatar').style.backgroundImage = `url(${session.fotoPerfilUrl || session.foto_perfil_url})`;
    $('#avatar').textContent = '';
  }
}

function renderMenu() {
  const menu = roleMenus[session.rol] || roleMenus.ESTUDIANTE;
  $('#menu').innerHTML = menu.map(([route, label]) => `<button data-route="${route}">${label}</button>`).join('');
  $('#menu').addEventListener('click', (e) => {
    const btn = e.target.closest('button[data-route]');
    if (!btn) return;
    navigate(btn.dataset.route);
    $('.sidebar').classList.remove('open');
  });
}

function bindGlobalActions() {
  $('#logout').addEventListener('click', () => { localStorage.removeItem('GPA_SESSION'); location.href = 'index.html'; });
  $('#mobileMenu').addEventListener('click', () => $('.sidebar').classList.toggle('open'));
  $('#openProfile').addEventListener('click', openProfile);
  $('#openNotifications').addEventListener('click', renderNotificationsDrawer);
}

async function navigate(route) {
  state.route = route;
  $$('#menu button').forEach(btn => btn.classList.toggle('active', btn.dataset.route === route));
  const renderers = {
    dashboard: renderDashboard,
    usuarios: () => renderCrud(entityConfig.usuarios),
    practicas: () => renderCrud(entityConfig.practicas),
    cursos: () => renderCrud(entityConfig.cursos),
    instituciones: () => renderInstitutionModule(),
    grupos: () => renderCrud(entityConfig.grupos),
    asignaciones: () => renderCrud(entityConfig.asignaciones),
    actividades: () => renderActivitiesByRole(),
    reportes: renderReports,
    chat: renderChat,
    matricula: renderStudentEnrollment,
    documentos: renderStudentDocuments,
    notas: renderGrades,
    inquietudes: renderQuestions,
    rubricas: renderRubrics,
    preguntas: renderQuestionBank,
    evaluaciones: renderEvaluations,
    convenios: () => renderCrud(entityConfig.convenios),
    practicantes: renderInstitutionStudents,
    compatibilidad: () => renderCrud(entityConfig.compatibilidad),
    certificados: renderCertificates
  };
  await (renderers[route] || renderDashboard)();
}

async function renderDashboard() {
  if (session.rol === 'ESTUDIANTE') return renderStudentDashboard();
  if (session.rol === 'COORDINADOR') return renderCoordinatorDashboard();
  if (session.rol === 'DOCENTE') return renderTeacherDashboard();
  if (session.rol === 'INSTITUCION') return renderInstitutionDashboard();
  return renderDirectorDashboard();
}

async function loadAll() {
  const keys = ['usuarios', 'practicas', 'cursos', 'instituciones', 'grupos', 'actividades', 'notificaciones', 'convenios', 'compatibilidad'];
  const entries = await Promise.all(keys.map(async k => [k, await api.list(k).catch(() => [])]));
  state.cache = Object.fromEntries(entries);
  const userNotifs = state.cache.notificaciones.filter(n => Number(n.id_usuario) === Number(session.idUsuario || session.id_usuario) && n.estado === 'PENDIENTE');
  $('#notifCount').textContent = userNotifs.length;
  return state.cache;
}

async function renderDirectorDashboard() {
  title.textContent = 'Bienvenido Director';
  subtitle.textContent = 'Supervisión integral, indicadores y aprobaciones del programa.';
  const data = await loadAll();
  content.innerHTML = `
    <section class="grid cols-4">
      ${kpi('Usuarios activos', data.usuarios.filter(x => x.estado === 'ACTIVO').length, '👥')}
      ${kpi('Prácticas', data.practicas.length, '📘')}
      ${kpi('Instituciones aprobadas', data.instituciones.filter(x => x.estado === 'APROBADO').length, '🏫')}
      ${kpi('Horas registradas', sum(data.actividades, 'horas'), '⏱')}
    </section>
    <section class="grid cols-2">
      ${card('Estado general', stateBars(data.practicas.concat(data.instituciones, data.actividades)))}
      ${card('Actividad reciente', recentList(data.actividades, 'descripcion'))}
    </section>
    <section class="card">
      <h2>Acciones rápidas</h2>
      <div class="toolbar-left">
        <button class="btn primary" data-go="usuarios">Gestionar usuarios</button>
        <button class="btn primary" data-go="instituciones">Aprobar instituciones</button>
        <button class="btn primary" data-go="reportes">Generar reportes</button>
      </div>
    </section>`;
  bindGoButtons();
}

async function renderCoordinatorDashboard() {
  title.textContent = 'Bienvenido Coordinador';
  subtitle.textContent = 'Coordinación operativa, grupos, docentes, estudiantes e instituciones receptoras.';
  const data = await loadAll();
  content.innerHTML = `
    <section class="grid cols-4">
      ${kpi('Grupos activos', data.grupos.filter(x => x.estado === 'ACTIVO').length, '🧩')}
      ${kpi('Docentes asignados', data.usuarios.filter(x => x.rol === 'DOCENTE').length, '🧑‍🏫')}
      ${kpi('Estudiantes', data.usuarios.filter(x => x.rol === 'ESTUDIANTE').length, '🎓')}
      ${kpi('Pendientes', data.actividades.filter(x => x.estado === 'PENDIENTE').length, '⚠')}
    </section>
    <section class="grid cols-2">
      ${card('Funciones del coordinador', `<ul><li>Gestionar preinscripción y matrícula de práctica.</li><li>Aprobar planes de trabajo.</li><li>Asignar docentes y grupos.</li><li>Supervisar horas y reportes finales.</li><li>Relacionarse con instituciones receptoras.</li></ul>`)}
      ${card('Procesos pendientes', recentList(data.actividades.filter(x => x.estado === 'PENDIENTE'), 'descripcion'))}
    </section>`;
}

async function renderTeacherDashboard() {
  title.textContent = 'Bienvenido Docente Asesor';
  subtitle.textContent = 'Revisión de actividades, retroalimentación, preguntas, rúbricas y evaluación final.';
  const data = await loadAll();
  content.innerHTML = `
    <section class="grid cols-4">
      ${kpi('Por revisar', data.actividades.filter(x => x.estado === 'PENDIENTE').length, '📝')}
      ${kpi('Aprobadas', data.actividades.filter(x => x.estado === 'APROBADO').length, '✅')}
      ${kpi('Horas revisadas', sum(data.actividades.filter(x => x.estado === 'APROBADO'), 'horas'), '⏱')}
      ${kpi('Estudiantes', data.usuarios.filter(x => x.rol === 'ESTUDIANTE').length, '🎓')}
    </section>
    <section class="card">
      <h2>Revisión rápida de actividades</h2>
      <p class="muted">Abra una actividad para comparar descripción, horas, comentarios y estado. La retroalimentación usa texto simple para mantener la aplicación ligera.</p>
      ${renderCompactActivityList(data.actividades)}
    </section>`;
  bindActivityReview();
}

async function renderInstitutionDashboard() {
  title.textContent = 'Bienvenida Institución Receptora';
  subtitle.textContent = 'Gestión de practicantes, convenio, horas, evaluación final y certificación.';
  const data = await loadAll();
  content.innerHTML = `
    <section class="grid cols-3">
      ${kpi('Practicantes activos', data.usuarios.filter(x => x.rol === 'ESTUDIANTE').length, '🎓')}
      ${kpi('Horas por aprobar', sum(data.actividades.filter(x => x.estado === 'PENDIENTE'), 'horas'), '⏱')}
      ${kpi('Convenios vigentes', data.convenios.filter(x => x.estado === 'ACTIVO').length, '📄')}
    </section>
    <section class="grid cols-2">
      ${card('Flujo institucional', `<div class="step-list"><div class="step-item"><strong>Confirmar ingreso</strong><span>Validar datos, supervisor y acta de inicio.</span></div><div class="step-item"><strong>Aprobar horas</strong><span>Revisar bitácoras semanales y comentarios.</span></div><div class="step-item"><strong>Cerrar práctica</strong><span>Evaluar desempeño y habilitar certificado.</span></div></div>`)}
      ${card('Avance de horas', `<p>650 / 880 horas</p><div class="progress"><span style="width:74%"></span></div><p class="muted">Al llegar al 100% se desbloquea la evaluación y cierre.</p>`)}
    </section>`;
}

async function renderStudentDashboard() {
  title.textContent = 'Bienvenido Estudiante';
  subtitle.textContent = 'Paso a paso de práctica, documentos, actividades, notas y notificaciones pendientes.';
  const data = await loadAll();
  const myActivities = data.actividades.filter(a => Number(a.id_usuario) === Number(session.idUsuario || session.id_usuario));
  content.innerHTML = `
    <section class="grid cols-4">
      ${kpi('Pendientes', myActivities.filter(x => x.estado === 'PENDIENTE').length, '⚠')}
      ${kpi('Realizadas', myActivities.filter(x => x.estado === 'APROBADO').length, '✅')}
      ${kpi('Horas', sum(myActivities, 'horas'), '⏱')}
      ${kpi('Promedio', '4.3', '📊')}
    </section>
    <section class="grid cols-2">
      ${card('Paso a paso sugerido', studentSteps())}
      ${card('Compatibilidad con instituciones', metricBars(data.compatibilidad.filter(c => Number(c.id_usuario) === Number(session.idUsuario || session.id_usuario))))}
    </section>
    <section class="card">
      <h2>Actividades recientes</h2>
      ${renderCompactActivityList(myActivities)}
    </section>`;
  bindActivityReview();
}

function studentSteps() {
  return `<div class="step-list">
    <div class="step-item"><div><strong>Registro y hoja de vida</strong><p class="muted">Carga tu hoja de vida y completa tu perfil con foto.</p></div></div>
    <div class="step-item"><div><strong>Validación de matrícula</strong><p class="muted">Si no aparece matrícula, el sistema debe indicar: “Aún no tienes matrícula de práctica aprobada”.</p></div></div>
    <div class="step-item"><div><strong>Documentos de inicio</strong><p class="muted">Acta de inicio, ARL y datos de institución.</p></div></div>
    <div class="step-item"><div><strong>Actividades y bitácora</strong><p class="muted">Registra horas, tareas y evidencias. El docente e institución revisan.</p></div></div>
    <div class="step-item"><div><strong>Evaluación y certificado</strong><p class="muted">Responde evaluaciones y consulta nota, promedio y certificado.</p></div></div>
  </div>`;
}

async function renderCrud(config) {
  state.currentEntity = config.entity;
  title.textContent = config.title;
  subtitle.textContent = config.description;
  const rows = await api.list(config.entity).catch(() => []);
  state.cache[config.entity] = rows;
  content.innerHTML = `
    <section class="card">
      <div class="toolbar">
        <div class="toolbar-left">
          <input class="search" id="searchInput" placeholder="Buscar en ${config.title.toLowerCase()}...">
          ${tabsHtml(rows)}
        </div>
        <div class="toolbar-right">
          <button class="btn primary" id="newRecord">+ Nuevo</button>
        </div>
      </div>
      <div id="tableArea">${tableHtml(config, rows)}</div>
    </section>`;
  bindCrud(config, rows);
}

function renderInstitutionModule() {
  return renderCrud(entityConfig.instituciones).then(() => {
    const extra = document.createElement('section');
    extra.className = 'grid cols-2';
    extra.innerHTML = `${card('Qué debe mostrar cada institución', `<ul><li>Datos generales y estado del servicio.</li><li>Convenio activo con fechas y documento.</li><li>Estudiantes con historial en la institución.</li><li>Compatibilidad para recomendar prácticas.</li><li>CRUD completo y trazabilidad de revisión.</li></ul>`)}${card('Estados permitidos', `<p><span class="status ACTIVO">ACTIVO</span> <span class="status INACTIVO">INACTIVO</span> <span class="status PENDIENTE">PENDIENTE</span> <span class="status APROBADO">APROBADO</span> <span class="status REPROBADO">REPROBADO</span></p>`)} `;
    content.appendChild(extra);
  });
}

async function renderActivitiesByRole() {
  await renderCrud(entityConfig.actividades);
  const note = document.createElement('section');
  note.className = 'card';
  note.innerHTML = `<h2>Historial y revisión</h2><p class="muted">Al seleccionar “Ver”, se conservan descripción, comentarios y se muestra quién revisó o cargó la actividad. Para producción, guarde cada apertura en HISTORIAL_REVISION.</p>`;
  content.appendChild(note);
}

function bindCrud(config, sourceRows) {
  $('#newRecord').addEventListener('click', () => openRecordForm(config));
  $('#searchInput').addEventListener('input', () => refreshTable(config, sourceRows));
  $$('.tab').forEach(tab => tab.addEventListener('click', () => { state.activeTab = tab.dataset.status; refreshTable(config, sourceRows); }));
  $('#tableArea').addEventListener('click', async (e) => {
    const btn = e.target.closest('button[data-action]');
    if (!btn) return;
    const id = btn.dataset.id;
    const action = btn.dataset.action;
    if (action === 'edit') openRecordForm(config, sourceRows.find(r => String(r[config.id]) === String(id)));
    if (action === 'view') openDetail(config, sourceRows.find(r => String(r[config.id]) === String(id)));
    if (action === 'activate') { await api.setState(config.entity, id, 'ACTIVO'); navigate(state.route); }
    if (action === 'inactive') { await api.setState(config.entity, id, 'INACTIVO'); navigate(state.route); }
    if (action === 'approve') { await api.setState(config.entity, id, 'APROBADO'); navigate(state.route); }
    if (action === 'remove') { if (confirm('¿Desea eliminar o inactivar este registro?')) { await api.remove(config.entity, id); navigate(state.route); } }
  });
}

function refreshTable(config, rows) {
  const query = ($('#searchInput')?.value || '').toLowerCase();
  const filtered = rows.filter(row => {
    const matchesQuery = Object.values(row).join(' ').toLowerCase().includes(query);
    const matchesTab = state.activeTab === 'TODOS' || row.estado === state.activeTab;
    return matchesQuery && matchesTab;
  });
  $('#tableArea').innerHTML = tableHtml(config, filtered);
  $$('.tab').forEach(tab => tab.classList.toggle('active', tab.dataset.status === state.activeTab));
}

function tableHtml(config, rows) {
  const ordered = orderByState(rows);
  return `<div class="table-wrap"><table><thead><tr>${config.columns.map(c => `<th>${labelize(c)}</th>`).join('')}<th>Acciones</th></tr></thead><tbody>${ordered.map(row => `<tr>${config.columns.map(c => `<td>${formatCell(c, row[c])}</td>`).join('')}<td>${actionsHtml(config, row)}</td></tr>`).join('') || `<tr><td colspan="${config.columns.length + 1}">No hay registros.</td></tr>`}</tbody></table></div>`;
}

function actionsHtml(config, row) {
  const id = row[config.id];
  return `<div class="action-row">
    <button class="btn ghost" data-action="view" data-id="${id}">Ver</button>
    <button class="btn ghost" data-action="edit" data-id="${id}">Editar</button>
    <button class="btn success" data-action="activate" data-id="${id}">Activar</button>
    <button class="btn warning" data-action="inactive" data-id="${id}">Inactivar</button>
    <button class="btn primary" data-action="approve" data-id="${id}">Aprobar</button>
    <button class="btn danger" data-action="remove" data-id="${id}">Eliminar</button>
  </div>`;
}

function tabsHtml(rows) {
  const counts = estados().reduce((acc, st) => ({ ...acc, [st]: rows.filter(r => r.estado === st).length }), {});
  return `<div class="tabs"><button class="tab active" data-status="TODOS">Todos ${rows.length}</button>${estados().map(st => `<button class="tab" data-status="${st}">${st} ${counts[st] || 0}</button>`).join('')}</div>`;
}

function openRecordForm(config, record = null) {
  state.editingId = record ? record[config.id] : null;
  $('#modalTitle').textContent = record ? `Editar ${config.title}` : `Nuevo ${config.title}`;
  $('#modalFields').innerHTML = config.fields.map(([name, label, type, options]) => fieldHtml(name, label, type, options, record?.[name])).join('');
  $('#recordDialog').showModal();
  $('#saveRecord').onclick = async (event) => {
    event.preventDefault();
    const data = Object.fromEntries(new FormData($('#recordForm')).entries());
    if (record) await api.update(config.entity, record[config.id], data);
    else await api.create(config.entity, data);
    $('#recordDialog').close();
    navigate(state.route);
  };
}

function fieldHtml(name, label, type, options, value = '') {
  if (type === 'select') return `<label><span>${label}</span><select name="${name}">${options.map(o => `<option value="${o}" ${value === o ? 'selected' : ''}>${o}</option>`).join('')}</select></label>`;
  if (type === 'textarea') return `<label class="full"><span>${label}</span><textarea name="${name}" rows="4">${value ?? ''}</textarea></label>`;
  return `<label><span>${label}</span><input name="${name}" type="${type}" value="${value ?? ''}"></label>`;
}

function openDetail(config, row = {}) {
  $('#drawerTitle').textContent = `Detalle de ${config.title}`;
  $('#drawerContent').innerHTML = `<div class="grid">${Object.entries(row).map(([k, v]) => `<div class="card"><strong>${labelize(k)}</strong><p>${formatCell(k, v)}</p></div>`).join('')}</div><hr><h3>Historial de revisión</h3><p class="muted">En producción se consulta HISTORIAL_REVISION por entidad ${config.entity} e ID ${row[config.id]}.</p>`;
  $('#drawerDialog').showModal();
}

function renderReports() {
  title.textContent = 'Reportes'; subtitle.textContent = 'Indicadores para dirección, acreditación y seguimiento.';
  content.innerHTML = `<section class="grid cols-2">${card('Distribución por área', metricBars([{ criterios: 'Tecnología', porcentaje: 35 }, { criterios: 'Diseño', porcentaje: 25 }, { criterios: 'Marketing', porcentaje: 20 }, { criterios: 'Negocios', porcentaje: 20 }]))}${card('Generar reporte', `<label>Tipo de reporte<select><option>General</option><option>Prácticas</option><option>Estudiantes</option><option>Instituciones</option><option>Indicadores CNA/MEN</option></select></label><br><div class="grid cols-2"><label>Inicio<input type="date"></label><label>Fin<input type="date"></label></div><br><button class="btn primary full">Generar PDF / Excel</button>`)}</section><section class="card"><h2>Reportes recientes</h2>${recentList([{descripcion:'Reporte mensual - Marzo 2026'}, {descripcion:'Estadísticas de prácticas'}, {descripcion:'Rendimiento estudiantes'}], 'descripcion')}</section>`;
}

async function renderChat() {
  title.textContent = 'Chat interno'; subtitle.textContent = 'Comunicación privada entre usuarios, similar a correo interno.';
  const mensajes = await api.list('mensajes').catch(() => []);
  content.innerHTML = `<section class="chat-box"><div class="chat-list"><h3>Bandeja</h3>${mensajes.map(m => `<div class="chat-message"><strong>${m.asunto || 'Mensaje'}</strong><p>${m.mensaje}</p></div>`).join('')}</div><div class="chat-thread"><h3>Nuevo mensaje</h3><label>Destinatario ID<input id="msgTo" type="number" placeholder="Ej: 2"></label><label>Asunto<input id="msgSubject" placeholder="Asunto"></label><label>Mensaje<textarea id="msgText" rows="6"></textarea></label><br><button id="sendMsg" class="btn primary">Enviar</button></div></section>`;
  $('#sendMsg').addEventListener('click', async () => {
    await api.create('mensajes', { id_remitente: session.idUsuario || session.id_usuario, id_destinatario: Number($('#msgTo').value), asunto: $('#msgSubject').value, mensaje: $('#msgText').value, leido: 'N' });
    renderChat();
  });
}

function renderStudentEnrollment() { title.textContent = 'Mi matrícula'; subtitle.textContent = 'Estado de matrícula y asignación de práctica.'; content.innerHTML = `<section class="card"><h2>Aún no tienes matrícula de práctica aprobada</h2><p class="muted">Cuando el coordinador/docente asigne la práctica, aquí verás institución recomendada, docente asesor, fechas, horas y documentos requeridos.</p><button class="btn primary" data-go="dashboard">Ver paso a paso</button></section>`; bindGoButtons(); }
function renderStudentDocuments() { title.textContent = 'Documentos'; subtitle.textContent = 'Hoja de vida, ARL, acta de inicio, informes y evidencias.'; content.innerHTML = `${card('Carga de documentos', `<label>Tipo de documento<select><option>Hoja de vida</option><option>ARL</option><option>Acta de inicio</option><option>Informe de avance</option><option>Informe final</option></select></label><label>Archivo<input type="file"></label><br><button class="btn primary">Cargar documento</button>`)}`; }
function renderGrades() { title.textContent = 'Notas y promedio'; subtitle.textContent = 'Consulta de evaluación, rúbrica y promedio final.'; content.innerHTML = `<section class="grid cols-3">${kpi('Promedio', '4.3', '📊')}${kpi('Rúbrica', '87%', '✅')}${kpi('Horas', '10', '⏱')}</section>${card('Detalle de criterios', metricBars([{criterios:'Planeación pedagógica', porcentaje:90},{criterios:'Puntualidad', porcentaje:86},{criterios:'Reflexión crítica', porcentaje:82}]))}`; }
function renderQuestions() { title.textContent = 'Inquietudes'; subtitle.textContent = 'Canal para dudas prioritarias del estudiante.'; content.innerHTML = `${card('Nueva inquietud', `<label>Asunto<input placeholder="Ej: Duda sobre horas aprobadas"></label><label>Descripción<textarea rows="6"></textarea></label><br><button class="btn primary">Enviar inquietud</button>`)}`; }
function renderRubrics() { title.textContent = 'Rúbricas'; subtitle.textContent = 'Criterios, niveles y ponderaciones para evaluación objetiva.'; content.innerHTML = `${card('Rúbrica analítica', metricBars([{criterios:'Excelente', porcentaje:100},{criterios:'Bueno', porcentaje:80},{criterios:'Regular', porcentaje:60},{criterios:'Deficiente', porcentaje:40}]))}`; }
function renderQuestionBank() { title.textContent = 'Banco de preguntas'; subtitle.textContent = 'Gestión de preguntas para evaluaciones y cuestionarios.'; content.innerHTML = `${card('Preguntas', `<div class="toolbar"><button class="btn primary">+ Crear pregunta</button><button class="btn ghost">Compartir entre prácticas</button></div><p class="muted">Las preguntas se organizan por práctica, tema y estado.</p>`)}`; }
function renderEvaluations() { title.textContent = 'Evaluaciones'; subtitle.textContent = 'Registro de evaluaciones y retroalimentación obligatoria.'; content.innerHTML = `${card('Retroalimentación ligera', `<p class="muted">Se usa textarea y respuestas rápidas para evitar editores pesados y mejorar rendimiento.</p><button class="btn ghost">Excelente trabajo</button> <button class="btn ghost">Faltó profundizar</button> <button class="btn ghost">Requiere corrección</button><br><br><textarea rows="6" placeholder="Escriba retroalimentación final..."></textarea><br><br><button class="btn primary">Guardar evaluación</button>`)}`; }
function renderInstitutionStudents() { title.textContent = 'Practicantes'; subtitle.textContent = 'Estudiantes por ingresar, activos y finalizados.'; content.innerHTML = `${card('Mis practicantes', `<div class="tabs"><button class="tab active">Por ingresar</button><button class="tab">Activos</button><button class="tab">Finalizados</button></div><br>${recentList([{descripcion:'Juan Estudiante - Confirmar ingreso'}, {descripcion:'Ana López - Activa 45%'}, {descripcion:'Carlos Gómez - Finalizado'}], 'descripcion')}`)}`; }
function renderCertificates() { title.textContent = 'Certificados'; subtitle.textContent = 'Cierre y certificación de práctica.'; content.innerHTML = `${card('Certificado', `<p>Cuando las horas lleguen al 100% y la institución apruebe evaluación final, se habilita la descarga.</p><button class="btn success">Generar certificado</button>`)}`; }

function renderCompactActivityList(items) {
  return `<div class="table-wrap"><table><thead><tr><th>Fecha</th><th>Tipo</th><th>Horas</th><th>Estado</th><th>Acción</th></tr></thead><tbody>${items.map(a => `<tr><td>${a.fecha_actividad || ''}</td><td>${a.tipo_actividad || ''}</td><td>${a.horas || 0}</td><td>${formatStatus(a.estado)}</td><td><button class="btn ghost" data-review="${a.id_actividad}">Ver revisión</button></td></tr>`).join('')}</tbody></table></div>`;
}
function bindActivityReview() { $$('[data-review]').forEach(btn => btn.addEventListener('click', async () => { const act = (state.cache.actividades || await api.list('actividades')).find(a => String(a.id_actividad) === btn.dataset.review); $('#drawerTitle').textContent = 'Revisión de actividad'; $('#drawerContent').innerHTML = `<h3>${act.tipo_actividad}</h3><p><strong>Descripción:</strong> ${act.descripcion}</p><p><strong>Comentarios:</strong> ${act.comentarios || 'Sin comentarios'}</p><p><strong>Horas:</strong> ${act.horas}</p><p>${formatStatus(act.estado)}</p><label>Retroalimentación docente<textarea rows="5">${act.comentarios || ''}</textarea></label><br><div class="toolbar-left"><button class="btn success">Aprobar</button><button class="btn warning">Solicitar corrección</button><button class="btn danger">Rechazar</button></div>`; $('#drawerDialog').showModal(); })); }

async function renderNotificationsDrawer() { const notifs = await api.list('notificaciones').catch(() => []); $('#drawerTitle').textContent = 'Notificaciones'; $('#drawerContent').innerHTML = recentList(notifs.filter(n => Number(n.id_usuario) === Number(session.idUsuario || session.id_usuario)), 'mensaje'); $('#drawerDialog').showModal(); }

function openProfile() {
  $('#profileComment').value = session.comentarioPerfil || session.comentario_perfil || '';
  $('#profileDialog').showModal();
  $('#profilePhoto').onchange = (e) => {
    const file = e.target.files[0]; if (!file) return;
    const reader = new FileReader();
    reader.onload = () => { session.fotoPerfilUrl = reader.result; $('#avatar').style.backgroundImage = `url(${reader.result})`; $('#avatar').textContent = ''; };
    reader.readAsDataURL(file);
  };
  $('#saveProfile').onclick = async (event) => {
    event.preventDefault();
    session.comentarioPerfil = $('#profileComment').value;
    const payload = { foto_perfil_url: session.fotoPerfilUrl || '', comentario_perfil: session.comentarioPerfil };
    if ($('#newPassword').value) payload.password = $('#newPassword').value;
    await api.updateProfile(session.idUsuario || session.id_usuario, payload).catch(() => null);
    localStorage.setItem('GPA_SESSION', JSON.stringify(session));
    $('#profileDialog').close();
  };
}

function kpi(label, value, icon) { return `<article class="card kpi"><div><span>${label}</span><strong>${value}</strong></div><div class="kpi-icon">${icon}</div></article>`; }
function card(heading, body) { return `<article class="card"><h2>${heading}</h2>${body}</article>`; }
function recentList(items, field) { return `<div class="grid">${items.length ? items.slice(0, 6).map(item => `<div class="step-item"><div><strong>${item.titulo || item.nombre || item.nombre_grupo || 'Registro'}</strong><p class="muted">${item[field] || item.estado || ''}</p></div></div>`).join('') : '<p class="muted">Sin registros recientes.</p>'}</div>`; }
function metricBars(items) { return `<div class="metric-bars">${items.map(i => `<div class="metric-row"><span>${i.criterios || i.nombre || i.estado || 'Indicador'}</span><div class="progress"><span style="width:${Math.min(Number(i.porcentaje || 0),100)}%"></span></div><strong>${i.porcentaje || 0}%</strong></div>`).join('')}</div>`; }
function stateBars(items) { const counts = estados().map(st => ({ criterios: st, porcentaje: items.length ? Math.round(items.filter(x => x.estado === st).length * 100 / items.length) : 0 })); return metricBars(counts); }
function sum(items, field) { return items.reduce((acc, item) => acc + Number(item[field] || 0), 0); }
function formatStatus(value) { return `<span class="status ${value || 'PENDIENTE'}">${value || 'PENDIENTE'}</span>`; }
function formatCell(col, value) { if (col === 'estado') return formatStatus(value); if (value == null || value === '') return '<span class="muted">-</span>'; return String(value); }
function labelize(text) { return text.replace(/_/g, ' ').replace(/\b\w/g, c => c.toUpperCase()); }
function bindGoButtons() { $$('[data-go]').forEach(btn => btn.addEventListener('click', () => navigate(btn.dataset.go))); }
