-- phpMyAdmin SQL Dump
-- version 4.2.12deb2+deb8u2
-- http://www.phpmyadmin.net
--
-- Servidor: localhost
-- Tiempo de generación: 24-11-2017 a las 06:34:54
-- Versión del servidor: 5.5.58-0+deb8u1
-- Versión de PHP: 5.6.30-0+deb8u1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de datos: `proyecto`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `credenciales`
--

CREATE TABLE IF NOT EXISTS `credenciales` (
`idCredenciales` int(11) NOT NULL,
  `nombre` varchar(255) NOT NULL,
  `restricciones` varchar(500) DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `credenciales`
--

INSERT INTO `credenciales` (`idCredenciales`, `nombre`, `restricciones`) VALUES
(4, 'Administrador', NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `diagnosticos`
--

CREATE TABLE IF NOT EXISTS `diagnosticos` (
`idDiagnostico` int(11) NOT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `condiciones` varchar(255) DEFAULT NULL,
  `ph` varchar(255) DEFAULT NULL,
  `pco2` varchar(255) DEFAULT NULL,
  `hco3` varchar(255) DEFAULT NULL,
  `cl` varchar(255) DEFAULT NULL,
  `na` varchar(255) DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `diagnosticos`
--

INSERT INTO `diagnosticos` (`idDiagnostico`, `nombre`, `condiciones`, `ph`, `pco2`, `hco3`, `cl`, `na`) VALUES
(19, 'acidosis respiratoria', 'return ($hco3>26 ) && ($pco2>42 ) && ($ph<7.35 );', '1', '1', '1', '0', '0'),
(20, 'alcalosis metabólica', 'return ($pco2>42 ) && ($hco3>26 ) && ($ph>7.45 );', '1', '1', '1', '0', '0'),
(21, 'alcalosis respiratoria', 'return ($hco3<22 ) && ($pco2<38 ) && ($ph>7.45 );', '1', '1', '1', '0', '0'),
(23, 'acidosis metabolica', 'return ($ph < 7.35) && ($pco2 < 38) && ($hco3 < 40);', '1', '1', '1', '0', '0');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `diagnosticoscomp`
--

CREATE TABLE IF NOT EXISTS `diagnosticoscomp` (
`idDiagnostico` int(11) NOT NULL,
  `diagFinal` longtext NOT NULL,
  `idPaciente` int(11) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=72 DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `diagnosticoscomp`
--

INSERT INTO `diagnosticoscomp` (`idDiagnostico`, `diagFinal`, `idPaciente`) VALUES
(1, 'El paciente no sufre de nada', 1),
(2, 'El paciente no sufre de nada', 1),
(3, 'El paciente no sufre de nada', 1),
(4, 'El paciente no sufre de nada', 1),
(5, 'El paciente no sufre de nada', 1),
(6, 'El paciente no sufre de nada', 1),
(7, 'El paciente no sufre de nada', 1),
(8, 'El paciente no sufre de nada', 1),
(9, 'El paciente no sufre de nada', 3),
(10, 'El paciente no sufre de nada', 4),
(11, 'El paciente no sufre de nada', 4),
(12, 'El paciente no sufre de nada', 1),
(13, 'el paciente sufre de Muerto ', 1),
(14, 'El paciente no sufre de nada', 1),
(15, 'El paciente no sufre de nada', 1),
(16, 'el paciente sufre de Muerto ', 1),
(17, 'el paciente sufre de Sífilis ', 1),
(18, 'el paciente sufre de muerto ', 1),
(19, 'El paciente no sufre de nada', 1),
(20, 'el paciente sufre de muerto ', 1),
(21, 'el paciente sufre de cancer ', 1),
(22, 'el paciente sufre de cancer ', 1),
(23, 'el paciente sufre de muerto ', 1),
(24, 'el paciente sufre de muerto ', 1),
(25, 'el paciente sufre de muerto ', 1),
(26, 'el paciente sufre de sifilis muerto ', 1),
(27, 'el paciente sufre de cancer, ', 1),
(28, 'el paciente sufre de sifilis, muerto, cancer, ', 1),
(29, 'el paciente sufre de muerto, ', 1),
(30, 'el paciente sufre de muerto, ', 1),
(31, 'el paciente sufre de muerto, ', 1),
(32, 'el paciente sufre de sifilis, ', 1),
(33, 'el paciente sufre de muerto, ', 1),
(34, 'el paciente sufre de sifilis, muerto, ebola, ', 1),
(35, 'el paciente sufre de sifilis, ', 1),
(36, 'el paciente sufre de sifilis, ', 1),
(37, 'el paciente sufre de proyecto dic, ', 1),
(38, 'el paciente sufre de sifilis, ', 3),
(39, 'el paciente sufre de muerto, cancer, ', 1),
(40, 'el paciente sufre de muerto, ', 1),
(41, 'el paciente sufre de muerto, ', 6),
(42, 'El paciente no sufre de nada', 7),
(43, 'El paciente no sufre de nada', 7),
(44, 'El paciente no sufre de nada', 6),
(45, 'El paciente no sufre de nada', 0),
(46, 'el paciente sufre de dengue\n\n, ', 6),
(47, 'El paciente no sufre de nada', 9),
(48, 'El paciente no sufre de nada', 9),
(49, 'el paciente sufre de muerte, ', 9),
(50, 'el paciente sufre de muerte, acidosis metabolica, ', 9),
(51, 'el paciente sufre de acidosis metabolica, ', 9),
(52, 'el paciente sufre de muerte, acidosis respiratoria, ', 9),
(53, 'el paciente sufre de acidosis metabólica, ', 9),
(54, 'el paciente sufre de muerte, acidosis metabólica, ', 9),
(55, 'el paciente sufre de acidosis metabólica, ', 9),
(56, 'el paciente sufre de alcalosis respiratoria, ', 9),
(57, 'El paciente no sufre de nada', 9),
(58, 'El paciente no sufre de nada', 9),
(59, 'el paciente sufre de muerte, ', 9),
(60, 'El paciente no sufre de nada', 9),
(61, 'el paciente sufre de alcalosis respiratoria, ', 9),
(62, 'el paciente sufre de acidosis metabólica, ', 9),
(63, 'El paciente no sufre de nada', 9),
(64, 'el paciente sufre de muerte, acidosis respiratoria, ', 9),
(65, 'El paciente no sufre de nada', 9),
(66, 'El paciente no sufre de nada', 9),
(67, 'El paciente no sufre de nada', 9),
(68, 'El paciente no sufre de nada', 9),
(69, 'El paciente no sufre de nada', 9),
(70, 'el paciente sufre de acidosis metabolica, ', 9),
(71, 'el paciente sufre de acidosis metabolica, ', 9);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `estudios`
--

CREATE TABLE IF NOT EXISTS `estudios` (
`idEstudio` int(11) NOT NULL,
  `ph` double DEFAULT NULL,
  `pco2` double DEFAULT NULL,
  `hco3` double DEFAULT NULL,
  `cl` double DEFAULT NULL,
  `na` double DEFAULT NULL,
  `idPaciente` int(11) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `funciones`
--

CREATE TABLE IF NOT EXISTS `funciones` (
`idFunciones` int(11) NOT NULL,
  `nombre` varchar(255) NOT NULL,
  `funcion` varchar(500) NOT NULL,
  `condiciones` varchar(255) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `funciones`
--

INSERT INTO `funciones` (`idFunciones`, `nombre`, `funcion`, `condiciones`) VALUES
(2, 'ph', 'ph', ''),
(3, 'pco2', 'pco2', '(pH>4 )'),
(4, 'hco3', 'hco3', '-'),
(6, 'na', 'na', ''),
(7, 'cl', 'cl', '');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pacientes`
--

CREATE TABLE IF NOT EXISTS `pacientes` (
`idPacientes` int(11) NOT NULL,
  `nombre` varchar(255) NOT NULL,
  `apellido` varchar(255) NOT NULL,
  `sexo` varchar(255) NOT NULL,
  `altura` varchar(255) NOT NULL,
  `edad` int(11) NOT NULL,
  `peso` varchar(255) NOT NULL,
  `tipoSangre` varchar(255) NOT NULL,
  `medicoCabecera` int(11) NOT NULL,
  `ultimoEstudio` varchar(255) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `pacientes`
--

INSERT INTO `pacientes` (`idPacientes`, `nombre`, `apellido`, `sexo`, `altura`, `edad`, `peso`, `tipoSangre`, `medicoCabecera`, `ultimoEstudio`) VALUES
(9, 'Diego', 'Pertierra', 'M', '168.0', 18, '64.0', 'A+', 0, 'Sun Nov 19 22:41:39 GMT-03:00 2017');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarios`
--

CREATE TABLE IF NOT EXISTS `usuarios` (
`idUsuario` int(11) NOT NULL,
  `hospital` varchar(255) DEFAULT NULL,
  `nombre` varchar(255) NOT NULL,
  `apellido` varchar(255) NOT NULL,
  `usuario` varchar(255) NOT NULL,
  `contrasenia` varchar(255) NOT NULL,
  `ultimaSesion` date NOT NULL,
  `matricula` int(11) NOT NULL,
  `idCredencial` int(11) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `usuarios`
--

INSERT INTO `usuarios` (`idUsuario`, `hospital`, `nombre`, `apellido`, `usuario`, `contrasenia`, `ultimaSesion`, `matricula`, `idCredencial`) VALUES
(24, 'Durand', 'Enrico', 'Martella', 'enrico.martella', 'martella.enrico', '0000-00-00', 83692, 0);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `credenciales`
--
ALTER TABLE `credenciales`
 ADD PRIMARY KEY (`idCredenciales`);

--
-- Indices de la tabla `diagnosticos`
--
ALTER TABLE `diagnosticos`
 ADD PRIMARY KEY (`idDiagnostico`);

--
-- Indices de la tabla `diagnosticoscomp`
--
ALTER TABLE `diagnosticoscomp`
 ADD PRIMARY KEY (`idDiagnostico`);

--
-- Indices de la tabla `estudios`
--
ALTER TABLE `estudios`
 ADD PRIMARY KEY (`idEstudio`,`idPaciente`), ADD UNIQUE KEY `idEstudio` (`idEstudio`), ADD UNIQUE KEY `idPaciente` (`idPaciente`);

--
-- Indices de la tabla `funciones`
--
ALTER TABLE `funciones`
 ADD PRIMARY KEY (`idFunciones`);

--
-- Indices de la tabla `pacientes`
--
ALTER TABLE `pacientes`
 ADD PRIMARY KEY (`idPacientes`,`medicoCabecera`), ADD UNIQUE KEY `idPacientes` (`idPacientes`,`medicoCabecera`), ADD UNIQUE KEY `idPacientes_2` (`idPacientes`), ADD KEY `idMedicoCabecera` (`medicoCabecera`);

--
-- Indices de la tabla `usuarios`
--
ALTER TABLE `usuarios`
 ADD PRIMARY KEY (`idUsuario`), ADD KEY `idCredencial` (`idCredencial`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `credenciales`
--
ALTER TABLE `credenciales`
MODIFY `idCredenciales` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT de la tabla `diagnosticos`
--
ALTER TABLE `diagnosticos`
MODIFY `idDiagnostico` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=24;
--
-- AUTO_INCREMENT de la tabla `diagnosticoscomp`
--
ALTER TABLE `diagnosticoscomp`
MODIFY `idDiagnostico` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=72;
--
-- AUTO_INCREMENT de la tabla `estudios`
--
ALTER TABLE `estudios`
MODIFY `idEstudio` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=33;
--
-- AUTO_INCREMENT de la tabla `funciones`
--
ALTER TABLE `funciones`
MODIFY `idFunciones` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=12;
--
-- AUTO_INCREMENT de la tabla `pacientes`
--
ALTER TABLE `pacientes`
MODIFY `idPacientes` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=10;
--
-- AUTO_INCREMENT de la tabla `usuarios`
--
ALTER TABLE `usuarios`
MODIFY `idUsuario` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=25;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
