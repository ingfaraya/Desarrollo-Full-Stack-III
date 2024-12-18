"use strict";
/*
 * SonarQube JavaScript Plugin
 * Copyright (C) 2011-2024 SonarSource SA
 * mailto:info AT sonarsource DOT com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
// https://sonarsource.github.io/rspec/#/rspec/S5247/javascript
Object.defineProperty(exports, "__esModule", { value: true });
exports.rule = void 0;
const helpers_1 = require("../helpers");
const meta_1 = require("./meta");
const MESSAGE = 'Make sure disabling auto-escaping feature is safe here.';
exports.rule = {
    meta: (0, helpers_1.generateMeta)(meta_1.meta, undefined, true),
    create(context) {
        const services = context.sourceCode.parserServices;
        function isEmptySanitizerFunction(sanitizerFunction) {
            if (sanitizerFunction.params.length !== 1) {
                return false;
            }
            const firstParam = sanitizerFunction.params[0];
            if (firstParam.type !== 'Identifier') {
                return false;
            }
            const firstParamName = firstParam.name;
            if (sanitizerFunction.body.type !== 'BlockStatement') {
                return (sanitizerFunction.body.type === 'Identifier' &&
                    sanitizerFunction.body.name === firstParamName);
            }
            const { body } = sanitizerFunction.body;
            if (body.length !== 1) {
                return false;
            }
            const onlyStatement = body[0];
            if (onlyStatement.type === 'ReturnStatement' &&
                onlyStatement.argument &&
                (0, helpers_1.isIdentifier)(onlyStatement.argument, firstParamName)) {
                return true;
            }
            return false;
        }
        function isInvalidSanitizerFunction(node) {
            let assignedFunction = (0, helpers_1.getValueOfExpression)(context, node, 'FunctionExpression') ??
                (0, helpers_1.getValueOfExpression)(context, node, 'ArrowFunctionExpression');
            if (!assignedFunction && node.type === 'Identifier' && (0, helpers_1.isRequiredParserServices)(services)) {
                assignedFunction = (0, helpers_1.resolveFromFunctionReference)(context, node);
            }
            if (!!assignedFunction) {
                return isEmptySanitizerFunction(assignedFunction);
            }
            return false;
        }
        return {
            CallExpression: (node) => {
                const callExpression = node;
                const fqn = (0, helpers_1.getFullyQualifiedName)(context, callExpression);
                if (fqn === 'handlebars.compile') {
                    (0, helpers_1.checkSensitiveCall)(context, callExpression, 1, 'noEscape', true, MESSAGE);
                }
                if (fqn === 'marked.setOptions') {
                    (0, helpers_1.checkSensitiveCall)(context, callExpression, 0, 'sanitize', false, MESSAGE);
                }
                if (fqn === 'markdown-it') {
                    (0, helpers_1.checkSensitiveCall)(context, callExpression, 0, 'html', true, MESSAGE);
                }
            },
            NewExpression: (node) => {
                const newExpression = node;
                if ((0, helpers_1.getFullyQualifiedName)(context, newExpression) === 'kramed.Renderer') {
                    (0, helpers_1.checkSensitiveCall)(context, newExpression, 0, 'sanitize', false, MESSAGE);
                }
            },
            AssignmentExpression: (node) => {
                const assignmentExpression = node;
                const { left, right } = assignmentExpression;
                if (left.type !== 'MemberExpression') {
                    return;
                }
                if (!((0, helpers_1.getFullyQualifiedName)(context, left) === 'mustache.escape' ||
                    (isMustacheIdentifier(left.object) && (0, helpers_1.isIdentifier)(left.property, 'escape')))) {
                    return;
                }
                if (isInvalidSanitizerFunction(right)) {
                    (0, helpers_1.report)(context, {
                        node: left,
                        message: MESSAGE,
                    });
                }
            },
        };
    },
};
function isMustacheIdentifier(node) {
    return (0, helpers_1.isIdentifier)(node, 'Mustache');
}
//# sourceMappingURL=rule.js.map