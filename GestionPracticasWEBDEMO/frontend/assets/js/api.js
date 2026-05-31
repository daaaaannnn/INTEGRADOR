const API_BASE = localStorage.getItem('GPA_API_BASE') || 'http://localhost:8080/api';

const mockUsers = [
  { idUsuario: 1, id_usuario: 1, nombre: 'Director', apellido: 'Programa', correoElectronico: 'admin@proyectojd.com', correo_electronico: 'admin@proyectojd.com', rol: 'DIRECTOR', estado: 'ACTIVO', password: '123456' },
  { idUsuario: 2, id_usuario: 2, nombre: 'Juan', apellido: 'Estudiante', correoElectronico: 'juan@proyectojd.com', correo_electronico: 'juan@proyectojd.com', rol: 'ESTUDIANTE', estado: 'ACTIVO', password: '123456' },
  { idUsuario: 3, id_usuario: 3, nombre: 'Maria', apellido: 'Docente', correoElectronico: 'maria@proyectojd.com', correo_electronico: 'maria@proyectojd.com', rol: 'DOCENTE', estado: 'ACTIVO', password: '123456' },
  { idUsuario: 4, id_usuario: 4, nombre: 'Coordinador', apellido: 'Practica', correoElectronico: 'carlos@proyectojd.com', correo_electronico: 'carlos@proyectojd.com', rol: 'COORDINADOR', estado: 'ACTIVO', password: '123456' },
  { idUsuario: 5, id_usuario: 5, nombre: 'Institucion', apellido: 'Receptora', correoElectronico: 'empresa@proyectojd.com', correo_electronico: 'empresa@proyectojd.com', rol: 'INSTITUCION', estado: 'ACTIVO', password: '123456' }
];

const seed = {
  usuarios: mockUsers.map(({ password, ...u }) => ({ ...u, correo_electronico: u.correoElectronico })),
  practicas: [
    { id_practica: 1, nombre: 'Práctica Pedagógica I', tipo_modalidad: 'Presencial', horas_reglamentarias: 120, estado: 'ACTIVO', fecha_inicio: '2026-03-15', fecha_fin: '2026-06-01' },
    { id_practica: 2, nombre: 'Práctica Profesional', tipo_modalidad: 'Híbrida', horas_reglamentarias: 240, estado: 'PENDIENTE', fecha_inicio: '2026-04-01', fecha_fin: '2026-08-01' }
  ],
  cursos: [
    { id_curso: 1, nombre: 'Práctica Docente I', codigo: 'PRA101', semestre: 'IV', estado: 'ACTIVO' },
    { id_curso: 2, nombre: 'Seminario de Práctica', codigo: 'PRA202', semestre: 'V', estado: 'INACTIVO' }
  ],
  instituciones: [
    { id_institucion: 1, nombre: 'Colegio Técnico Integrado', nit: '900123456-1', ciudad: 'Bucaramanga', representante: 'Rector Académico', email: 'contacto@colegiotecnico.edu.co', telefono: '6070000000', estado: 'APROBADO' },
    { id_institucion: 2, nombre: 'Institución Educativa Nueva Esperanza', nit: '800999111-5', ciudad: 'Floridablanca', representante: 'Coordinadora Académica', email: 'practicas@nuevaesperanza.edu.co', telefono: '6071112222', estado: 'PENDIENTE' }
  ],
  grupos: [
    { id_grupo: 1, nombre_grupo: 'Grupo A-2026', periodo_academico: 'I-2026', cupo_maximo: 30, estado: 'ACTIVO', id_docente: 3, id_institucion: 1 },
    { id_grupo: 2, nombre_grupo: 'Grupo B-2026', periodo_academico: 'I-2026', cupo_maximo: 25, estado: 'PENDIENTE', id_docente: 3, id_institucion: 2 }
  ],
  asignaciones: [
    { id_asignacion_docente: 1, id_usuario: 3, id_grupo: 1, fecha_asignacion: '2026-03-20', estado: 'ACTIVO' }
  ],
  actividades: [
    { id_actividad: 1, id_usuario: 2, id_institucion: 1, fecha_actividad: '2026-05-20', tipo_actividad: 'Observación de clase', horas: 4, descripcion: 'Registro de observación pedagógica inicial.', estado: 'PENDIENTE', comentarios: 'Pendiente por revisión docente.' },
    { id_actividad: 2, id_usuario: 2, id_institucion: 1, fecha_actividad: '2026-05-23', tipo_actividad: 'Apoyo pedagógico', horas: 6, descripcion: 'Acompañamiento a grupo de primaria.', estado: 'APROBADO', comentarios: 'Actividad validada por institución.' }
  ],
  convenios: [
    { id_convenio: 1, id_institucion: 1, numero_convenio: 'CONV-UDI-2026-001', fecha_inicio: '2026-03-01', fecha_fin: '2028-03-01', estado: 'ACTIVO', objeto: 'Convenio de cooperación para prácticas académicas.' }
  ],
  compatibilidad: [
    { id_compatibilidad: 1, id_usuario: 2, id_institucion: 1, porcentaje: 87, criterios: 'Alta coincidencia en pedagogía, ubicación e interés académico.' },
    { id_compatibilidad: 2, id_usuario: 2, id_institucion: 2, porcentaje: 72, criterios: 'Coincidencia media por modalidad y disponibilidad.' }
  ],
  notificaciones: [
    { id_notificacion: 1, id_usuario: 2, titulo: 'Documento pendiente', mensaje: 'Carga tu hoja de vida y acta de inicio para continuar.', tipo: 'PENDIENTE', estado: 'PENDIENTE' },
    { id_notificacion: 2, id_usuario: 3, titulo: 'Actividad por revisar', mensaje: 'Tienes actividades de estudiantes pendientes por retroalimentar.', tipo: 'ALERTA', estado: 'PENDIENTE' }
  ],
  mensajes: [
    { id_mensaje: 1, id_remitente: 1, id_destinatario: 2, asunto: 'Bienvenida', mensaje: 'Revisa tu paso a paso para iniciar la práctica.', leido: 'N' }
  ],
  historial: [
    { id_historial: 1, entidad: 'REGISTRO_ACTIVIDAD', id_entidad: 1, id_usuario: 3, accion: 'REVISION', descripcion: 'El docente abrió la actividad y verificó la descripción.', fecha_accion: '2026-05-24' }
  ]
};

function loadStore() {
  const raw = localStorage.getItem('GPA_STORE');
  if (raw) return JSON.parse(raw);
  localStorage.setItem('GPA_STORE', JSON.stringify(seed));
  return structuredClone(seed);
}

function saveStore(store) { localStorage.setItem('GPA_STORE', JSON.stringify(store)); }
function normalizeEndpoint(endpoint) { return endpoint.replace(/^\//, '').split('?')[0]; }
function idFieldFor(entity) {
  return ({ usuarios: 'id_usuario', practicas: 'id_practica', cursos: 'id_curso', instituciones: 'id_institucion', grupos: 'id_grupo', asignaciones: 'id_asignacion_docente', actividades: 'id_actividad', mensajes: 'id_mensaje', notificaciones: 'id_notificacion', convenios: 'id_convenio', compatibilidad: 'id_compatibilidad', historial: 'id_historial' })[entity] || 'id';
}

async function request(endpoint, options = {}) {
  try {
    const response = await fetch(`${API_BASE}${endpoint}`, {
      headers: { 'Content-Type': 'application/json', ...(options.headers || {}) },
      ...options
    });
    const json = await response.json().catch(() => ({}));
    if (!response.ok || json.ok === false) throw new Error(json.message || 'Error en la solicitud');
    return json.data ?? json;
  } catch (error) {
    return mockRequest(endpoint, options);
  }
}

async function mockRequest(endpoint, options = {}) {
  const store = loadStore();
  const path = normalizeEndpoint(endpoint);
  const method = (options.method || 'GET').toUpperCase();
  const query = new URLSearchParams(endpoint.split('?')[1] || '');

  if (path === 'auth/login') {
    const body = JSON.parse(options.body || '{}');
    const user = mockUsers.find(u => u.correoElectronico.toLowerCase() === String(body.correo || '').toLowerCase() && u.password === body.password && u.estado !== 'INACTIVO');
    if (!user) throw new Error('Credenciales inválidas o usuario inactivo');
    return { ...user, password: undefined };
  }

  if (path === 'perfil') return { actualizado: true };

  const [entity, action] = path.split('/');
  if (!store[entity]) throw new Error(`Endpoint no disponible: ${endpoint}`);
  const idField = idFieldFor(entity);

  if (method === 'GET') {
    if (query.has('id')) return store[entity].find(item => String(item[idField]) === query.get('id')) || {};
    if (query.has('campo') && query.has('valor')) return store[entity].filter(item => String(item[query.get('campo')]) === query.get('valor'));
    return orderByState(store[entity]);
  }

  const body = JSON.parse(options.body || '{}');
  if (method === 'POST') {
    const nextId = Math.max(0, ...store[entity].map(item => Number(item[idField] || 0))) + 1;
    const record = { [idField]: nextId, ...body };
    store[entity].push(record); saveStore(store); return { filas: 1, record };
  }
  if (method === 'PUT' && action === 'estado') {
    const record = store[entity].find(item => String(item[idField]) === query.get('id'));
    if (record) record.estado = body.estado;
    saveStore(store); return { filas: record ? 1 : 0 };
  }
  if (method === 'PUT') {
    const record = store[entity].find(item => String(item[idField]) === query.get('id'));
    if (record) Object.assign(record, body);
    saveStore(store); return { filas: record ? 1 : 0 };
  }
  if (method === 'DELETE') {
    const record = store[entity].find(item => String(item[idField]) === query.get('id'));
    if (record && 'estado' in record) record.estado = 'INACTIVO';
    saveStore(store); return { filas: record ? 1 : 0 };
  }
  return {};
}

function orderByState(items) {
  const order = { ACTIVO: 1, PENDIENTE: 2, APROBADO: 3, INACTIVO: 4, REPROBADO: 5, FINALIZADO: 6 };
  return [...items].sort((a,b) => (order[a.estado] || 99) - (order[b.estado] || 99));
}

export const api = {
  login: (correo, password) => request('/auth/login', { method: 'POST', body: JSON.stringify({ correo, password }) }),
  list: (entity) => request(`/${entity}`),
  get: (entity, id) => request(`/${entity}?id=${id}`),
  create: (entity, data) => request(`/${entity}`, { method: 'POST', body: JSON.stringify(data) }),
  update: (entity, id, data) => request(`/${entity}?id=${id}`, { method: 'PUT', body: JSON.stringify(data) }),
  setState: (entity, id, estado) => request(`/${entity}/estado?id=${id}`, { method: 'PUT', body: JSON.stringify({ estado }) }),
  remove: (entity, id) => request(`/${entity}?id=${id}`, { method: 'DELETE' }),
  updateProfile: (id, data) => request(`/perfil?id=${id}`, { method: 'PUT', body: JSON.stringify(data) })
};

export { orderByState };
