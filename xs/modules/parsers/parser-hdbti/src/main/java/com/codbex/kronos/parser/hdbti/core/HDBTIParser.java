/*
 * Copyright (c) 2022 codbex or an codbex affiliate company and contributors
 *
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-FileCopyrightText: 2022 codbex or an codbex affiliate company and contributors
 * SPDX-License-Identifier: EPL-2.0
 */
// Generated from com/codbex/kronos/parser/hdbti/core/HDBTI.g4 by ANTLR 4.13.2
package com.codbex.kronos.parser.hdbti.core;

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue", "this-escape"})
public class HDBTIParser extends Parser {
    static {
        RuntimeMetaData.checkVersion("4.13.2", RuntimeMetaData.VERSION);
    }

    protected static final DFA[] _decisionToDFA;
    protected static final PredictionContextCache _sharedContextCache = new PredictionContextCache();
    public static final int T__0 = 1, T__1 = 2, T__2 = 3, T__3 = 4, T__4 = 5, T__5 = 6, T__6 = 7, T__7 = 8, T__8 = 9, T__9 = 10, T__10 = 11,
            T__11 = 12, T__12 = 13, T__13 = 14, T__14 = 15, T__15 = 16, T__16 = 17, STRING = 18, BOOLEAN = 19, TRUE = 20, FALSE = 21,
            WS = 22, RB = 23, LB = 24, EQ = 25, LINE_COMMENT = 26, COMMENT = 27;
    public static final int RULE_importArr = 0, RULE_objConfig = 1, RULE_assignExpression = 2, RULE_assignTable = 3, RULE_assignSchema = 4,
            RULE_assignFile = 5, RULE_assignHeader = 6, RULE_assignUseHeaderNames = 7, RULE_assignDelimField = 8,
            RULE_assignDelimEnclosing = 9, RULE_assignDistinguishEmptyFromNull = 10, RULE_assignKeys = 11, RULE_keyArr = 12, RULE_pair = 13,
            RULE_pairKey = 14, RULE_pairValue = 15, RULE_tableName = 16;

    private static String[] makeRuleNames() {
        return new String[] {"importArr", "objConfig", "assignExpression", "assignTable", "assignSchema", "assignFile", "assignHeader",
                "assignUseHeaderNames", "assignDelimField", "assignDelimEnclosing", "assignDistinguishEmptyFromNull", "assignKeys",
                "keyArr", "pair", "pairKey", "pairValue", "tableName"};
    }

    public static final String[] ruleNames = makeRuleNames();

    private static String[] makeLiteralNames() {
        return new String[] {null, "'import'", "','", "';'", "'{'", "'}'", "'schema'", "'file'", "'header'", "'useHeaderNames'",
                "'delimField'", "'delimEnclosing'", "'distinguishEmptyFromNull'", "'keys'", "':'", "'table'", "'hdbtable'", "'cdstable'",
                null, null, "'true'", "'false'", null, "'['", "']'", "'='"};
    }

    private static final String[] _LITERAL_NAMES = makeLiteralNames();

    private static String[] makeSymbolicNames() {
        return new String[] {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
                "STRING", "BOOLEAN", "TRUE", "FALSE", "WS", "RB", "LB", "EQ", "LINE_COMMENT", "COMMENT"};
    }

    private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
    public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

    /**
     * @deprecated Use {@link #VOCABULARY} instead.
     */
    @Deprecated
    public static final String[] tokenNames;
    static {
        tokenNames = new String[_SYMBOLIC_NAMES.length];
        for (int i = 0; i < tokenNames.length; i++) {
            tokenNames[i] = VOCABULARY.getLiteralName(i);
            if (tokenNames[i] == null) {
                tokenNames[i] = VOCABULARY.getSymbolicName(i);
            }

            if (tokenNames[i] == null) {
                tokenNames[i] = "<INVALID>";
            }
        }
    }

    @Override
    @Deprecated
    public String[] getTokenNames() {
        return tokenNames;
    }

    @Override

    public Vocabulary getVocabulary() {
        return VOCABULARY;
    }

    @Override
    public String getGrammarFileName() {
        return "HDBTI.g4";
    }

    @Override
    public String[] getRuleNames() {
        return ruleNames;
    }

    @Override
    public String getSerializedATN() {
        return _serializedATN;
    }

    @Override
    public ATN getATN() {
        return _ATN;
    }

    public HDBTIParser(TokenStream input) {
        super(input);
        _interp = new ParserATNSimulator(this, _ATN, _decisionToDFA, _sharedContextCache);
    }

    @SuppressWarnings("CheckReturnValue")
    public static class ImportArrContext extends ParserRuleContext {
        public TerminalNode EQ() {
            return getToken(HDBTIParser.EQ, 0);
        }

        public TerminalNode RB() {
            return getToken(HDBTIParser.RB, 0);
        }

        public TerminalNode LB() {
            return getToken(HDBTIParser.LB, 0);
        }

        public List<ObjConfigContext> objConfig() {
            return getRuleContexts(ObjConfigContext.class);
        }

        public ObjConfigContext objConfig(int i) {
            return getRuleContext(ObjConfigContext.class, i);
        }

        public ImportArrContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_importArr;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof HDBTIListener)
                ((HDBTIListener) listener).enterImportArr(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof HDBTIListener)
                ((HDBTIListener) listener).exitImportArr(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof HDBTIVisitor)
                return ((HDBTIVisitor<? extends T>) visitor).visitImportArr(this);
            else
                return visitor.visitChildren(this);
        }
    }

    public final ImportArrContext importArr() throws RecognitionException {
        ImportArrContext _localctx = new ImportArrContext(_ctx, getState());
        enterRule(_localctx, 0, RULE_importArr);
        int _la;
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(34);
                match(T__0);
                setState(35);
                match(EQ);
                setState(36);
                match(RB);
                setState(45);
                _errHandler.sync(this);
                _la = _input.LA(1);
                if (_la == T__3) {
                    {
                        setState(37);
                        objConfig();
                        setState(42);
                        _errHandler.sync(this);
                        _la = _input.LA(1);
                        while (_la == T__1) {
                            {
                                {
                                    setState(38);
                                    match(T__1);
                                    setState(39);
                                    objConfig();
                                }
                            }
                            setState(44);
                            _errHandler.sync(this);
                            _la = _input.LA(1);
                        }
                    }
                }

                setState(47);
                match(LB);
                setState(48);
                match(T__2);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class ObjConfigContext extends ParserRuleContext {
        public List<AssignExpressionContext> assignExpression() {
            return getRuleContexts(AssignExpressionContext.class);
        }

        public AssignExpressionContext assignExpression(int i) {
            return getRuleContext(AssignExpressionContext.class, i);
        }

        public ObjConfigContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_objConfig;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof HDBTIListener)
                ((HDBTIListener) listener).enterObjConfig(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof HDBTIListener)
                ((HDBTIListener) listener).exitObjConfig(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof HDBTIVisitor)
                return ((HDBTIVisitor<? extends T>) visitor).visitObjConfig(this);
            else
                return visitor.visitChildren(this);
        }
    }

    public final ObjConfigContext objConfig() throws RecognitionException {
        ObjConfigContext _localctx = new ObjConfigContext(_ctx, getState());
        enterRule(_localctx, 2, RULE_objConfig);
        int _la;
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(50);
                match(T__3);
                setState(54);
                _errHandler.sync(this);
                _la = _input.LA(1);
                while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 245696L) != 0)) {
                    {
                        {
                            setState(51);
                            assignExpression();
                        }
                    }
                    setState(56);
                    _errHandler.sync(this);
                    _la = _input.LA(1);
                }
                setState(57);
                match(T__4);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class AssignExpressionContext extends ParserRuleContext {
        public AssignTableContext assignTable() {
            return getRuleContext(AssignTableContext.class, 0);
        }

        public AssignSchemaContext assignSchema() {
            return getRuleContext(AssignSchemaContext.class, 0);
        }

        public AssignFileContext assignFile() {
            return getRuleContext(AssignFileContext.class, 0);
        }

        public AssignHeaderContext assignHeader() {
            return getRuleContext(AssignHeaderContext.class, 0);
        }

        public AssignUseHeaderNamesContext assignUseHeaderNames() {
            return getRuleContext(AssignUseHeaderNamesContext.class, 0);
        }

        public AssignDelimFieldContext assignDelimField() {
            return getRuleContext(AssignDelimFieldContext.class, 0);
        }

        public AssignDelimEnclosingContext assignDelimEnclosing() {
            return getRuleContext(AssignDelimEnclosingContext.class, 0);
        }

        public AssignDistinguishEmptyFromNullContext assignDistinguishEmptyFromNull() {
            return getRuleContext(AssignDistinguishEmptyFromNullContext.class, 0);
        }

        public AssignKeysContext assignKeys() {
            return getRuleContext(AssignKeysContext.class, 0);
        }

        public AssignExpressionContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_assignExpression;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof HDBTIListener)
                ((HDBTIListener) listener).enterAssignExpression(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof HDBTIListener)
                ((HDBTIListener) listener).exitAssignExpression(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof HDBTIVisitor)
                return ((HDBTIVisitor<? extends T>) visitor).visitAssignExpression(this);
            else
                return visitor.visitChildren(this);
        }
    }

    public final AssignExpressionContext assignExpression() throws RecognitionException {
        AssignExpressionContext _localctx = new AssignExpressionContext(_ctx, getState());
        enterRule(_localctx, 4, RULE_assignExpression);
        try {
            setState(68);
            _errHandler.sync(this);
            switch (_input.LA(1)) {
                case T__14:
                case T__15:
                case T__16:
                    enterOuterAlt(_localctx, 1); {
                    setState(59);
                    assignTable();
                }
                    break;
                case T__5:
                    enterOuterAlt(_localctx, 2); {
                    setState(60);
                    assignSchema();
                }
                    break;
                case T__6:
                    enterOuterAlt(_localctx, 3); {
                    setState(61);
                    assignFile();
                }
                    break;
                case T__7:
                    enterOuterAlt(_localctx, 4); {
                    setState(62);
                    assignHeader();
                }
                    break;
                case T__8:
                    enterOuterAlt(_localctx, 5); {
                    setState(63);
                    assignUseHeaderNames();
                }
                    break;
                case T__9:
                    enterOuterAlt(_localctx, 6); {
                    setState(64);
                    assignDelimField();
                }
                    break;
                case T__10:
                    enterOuterAlt(_localctx, 7); {
                    setState(65);
                    assignDelimEnclosing();
                }
                    break;
                case T__11:
                    enterOuterAlt(_localctx, 8); {
                    setState(66);
                    assignDistinguishEmptyFromNull();
                }
                    break;
                case T__12:
                    enterOuterAlt(_localctx, 9); {
                    setState(67);
                    assignKeys();
                }
                    break;
                default:
                    throw new NoViableAltException(this);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class AssignTableContext extends ParserRuleContext {
        public TableNameContext tableName() {
            return getRuleContext(TableNameContext.class, 0);
        }

        public TerminalNode EQ() {
            return getToken(HDBTIParser.EQ, 0);
        }

        public TerminalNode STRING() {
            return getToken(HDBTIParser.STRING, 0);
        }

        public AssignTableContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_assignTable;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof HDBTIListener)
                ((HDBTIListener) listener).enterAssignTable(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof HDBTIListener)
                ((HDBTIListener) listener).exitAssignTable(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof HDBTIVisitor)
                return ((HDBTIVisitor<? extends T>) visitor).visitAssignTable(this);
            else
                return visitor.visitChildren(this);
        }
    }

    public final AssignTableContext assignTable() throws RecognitionException {
        AssignTableContext _localctx = new AssignTableContext(_ctx, getState());
        enterRule(_localctx, 6, RULE_assignTable);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(70);
                tableName();
                setState(71);
                match(EQ);
                setState(72);
                match(STRING);
                setState(73);
                match(T__2);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class AssignSchemaContext extends ParserRuleContext {
        public TerminalNode EQ() {
            return getToken(HDBTIParser.EQ, 0);
        }

        public TerminalNode STRING() {
            return getToken(HDBTIParser.STRING, 0);
        }

        public AssignSchemaContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_assignSchema;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof HDBTIListener)
                ((HDBTIListener) listener).enterAssignSchema(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof HDBTIListener)
                ((HDBTIListener) listener).exitAssignSchema(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof HDBTIVisitor)
                return ((HDBTIVisitor<? extends T>) visitor).visitAssignSchema(this);
            else
                return visitor.visitChildren(this);
        }
    }

    public final AssignSchemaContext assignSchema() throws RecognitionException {
        AssignSchemaContext _localctx = new AssignSchemaContext(_ctx, getState());
        enterRule(_localctx, 8, RULE_assignSchema);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(75);
                match(T__5);
                setState(76);
                match(EQ);
                setState(77);
                match(STRING);
                setState(78);
                match(T__2);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class AssignFileContext extends ParserRuleContext {
        public TerminalNode EQ() {
            return getToken(HDBTIParser.EQ, 0);
        }

        public TerminalNode STRING() {
            return getToken(HDBTIParser.STRING, 0);
        }

        public AssignFileContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_assignFile;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof HDBTIListener)
                ((HDBTIListener) listener).enterAssignFile(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof HDBTIListener)
                ((HDBTIListener) listener).exitAssignFile(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof HDBTIVisitor)
                return ((HDBTIVisitor<? extends T>) visitor).visitAssignFile(this);
            else
                return visitor.visitChildren(this);
        }
    }

    public final AssignFileContext assignFile() throws RecognitionException {
        AssignFileContext _localctx = new AssignFileContext(_ctx, getState());
        enterRule(_localctx, 10, RULE_assignFile);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(80);
                match(T__6);
                setState(81);
                match(EQ);
                setState(82);
                match(STRING);
                setState(83);
                match(T__2);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class AssignHeaderContext extends ParserRuleContext {
        public TerminalNode EQ() {
            return getToken(HDBTIParser.EQ, 0);
        }

        public TerminalNode BOOLEAN() {
            return getToken(HDBTIParser.BOOLEAN, 0);
        }

        public AssignHeaderContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_assignHeader;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof HDBTIListener)
                ((HDBTIListener) listener).enterAssignHeader(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof HDBTIListener)
                ((HDBTIListener) listener).exitAssignHeader(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof HDBTIVisitor)
                return ((HDBTIVisitor<? extends T>) visitor).visitAssignHeader(this);
            else
                return visitor.visitChildren(this);
        }
    }

    public final AssignHeaderContext assignHeader() throws RecognitionException {
        AssignHeaderContext _localctx = new AssignHeaderContext(_ctx, getState());
        enterRule(_localctx, 12, RULE_assignHeader);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(85);
                match(T__7);
                setState(86);
                match(EQ);
                setState(87);
                match(BOOLEAN);
                setState(88);
                match(T__2);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class AssignUseHeaderNamesContext extends ParserRuleContext {
        public TerminalNode EQ() {
            return getToken(HDBTIParser.EQ, 0);
        }

        public TerminalNode BOOLEAN() {
            return getToken(HDBTIParser.BOOLEAN, 0);
        }

        public AssignUseHeaderNamesContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_assignUseHeaderNames;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof HDBTIListener)
                ((HDBTIListener) listener).enterAssignUseHeaderNames(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof HDBTIListener)
                ((HDBTIListener) listener).exitAssignUseHeaderNames(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof HDBTIVisitor)
                return ((HDBTIVisitor<? extends T>) visitor).visitAssignUseHeaderNames(this);
            else
                return visitor.visitChildren(this);
        }
    }

    public final AssignUseHeaderNamesContext assignUseHeaderNames() throws RecognitionException {
        AssignUseHeaderNamesContext _localctx = new AssignUseHeaderNamesContext(_ctx, getState());
        enterRule(_localctx, 14, RULE_assignUseHeaderNames);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(90);
                match(T__8);
                setState(91);
                match(EQ);
                setState(92);
                match(BOOLEAN);
                setState(93);
                match(T__2);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class AssignDelimFieldContext extends ParserRuleContext {
        public TerminalNode EQ() {
            return getToken(HDBTIParser.EQ, 0);
        }

        public TerminalNode STRING() {
            return getToken(HDBTIParser.STRING, 0);
        }

        public AssignDelimFieldContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_assignDelimField;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof HDBTIListener)
                ((HDBTIListener) listener).enterAssignDelimField(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof HDBTIListener)
                ((HDBTIListener) listener).exitAssignDelimField(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof HDBTIVisitor)
                return ((HDBTIVisitor<? extends T>) visitor).visitAssignDelimField(this);
            else
                return visitor.visitChildren(this);
        }
    }

    public final AssignDelimFieldContext assignDelimField() throws RecognitionException {
        AssignDelimFieldContext _localctx = new AssignDelimFieldContext(_ctx, getState());
        enterRule(_localctx, 16, RULE_assignDelimField);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(95);
                match(T__9);
                setState(96);
                match(EQ);
                setState(97);
                match(STRING);
                setState(98);
                match(T__2);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class AssignDelimEnclosingContext extends ParserRuleContext {
        public TerminalNode EQ() {
            return getToken(HDBTIParser.EQ, 0);
        }

        public TerminalNode STRING() {
            return getToken(HDBTIParser.STRING, 0);
        }

        public AssignDelimEnclosingContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_assignDelimEnclosing;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof HDBTIListener)
                ((HDBTIListener) listener).enterAssignDelimEnclosing(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof HDBTIListener)
                ((HDBTIListener) listener).exitAssignDelimEnclosing(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof HDBTIVisitor)
                return ((HDBTIVisitor<? extends T>) visitor).visitAssignDelimEnclosing(this);
            else
                return visitor.visitChildren(this);
        }
    }

    public final AssignDelimEnclosingContext assignDelimEnclosing() throws RecognitionException {
        AssignDelimEnclosingContext _localctx = new AssignDelimEnclosingContext(_ctx, getState());
        enterRule(_localctx, 18, RULE_assignDelimEnclosing);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(100);
                match(T__10);
                setState(101);
                match(EQ);
                setState(102);
                match(STRING);
                setState(103);
                match(T__2);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class AssignDistinguishEmptyFromNullContext extends ParserRuleContext {
        public TerminalNode EQ() {
            return getToken(HDBTIParser.EQ, 0);
        }

        public TerminalNode BOOLEAN() {
            return getToken(HDBTIParser.BOOLEAN, 0);
        }

        public AssignDistinguishEmptyFromNullContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_assignDistinguishEmptyFromNull;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof HDBTIListener)
                ((HDBTIListener) listener).enterAssignDistinguishEmptyFromNull(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof HDBTIListener)
                ((HDBTIListener) listener).exitAssignDistinguishEmptyFromNull(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof HDBTIVisitor)
                return ((HDBTIVisitor<? extends T>) visitor).visitAssignDistinguishEmptyFromNull(this);
            else
                return visitor.visitChildren(this);
        }
    }

    public final AssignDistinguishEmptyFromNullContext assignDistinguishEmptyFromNull() throws RecognitionException {
        AssignDistinguishEmptyFromNullContext _localctx = new AssignDistinguishEmptyFromNullContext(_ctx, getState());
        enterRule(_localctx, 20, RULE_assignDistinguishEmptyFromNull);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(105);
                match(T__11);
                setState(106);
                match(EQ);
                setState(107);
                match(BOOLEAN);
                setState(108);
                match(T__2);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class AssignKeysContext extends ParserRuleContext {
        public TerminalNode EQ() {
            return getToken(HDBTIParser.EQ, 0);
        }

        public KeyArrContext keyArr() {
            return getRuleContext(KeyArrContext.class, 0);
        }

        public AssignKeysContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_assignKeys;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof HDBTIListener)
                ((HDBTIListener) listener).enterAssignKeys(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof HDBTIListener)
                ((HDBTIListener) listener).exitAssignKeys(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof HDBTIVisitor)
                return ((HDBTIVisitor<? extends T>) visitor).visitAssignKeys(this);
            else
                return visitor.visitChildren(this);
        }
    }

    public final AssignKeysContext assignKeys() throws RecognitionException {
        AssignKeysContext _localctx = new AssignKeysContext(_ctx, getState());
        enterRule(_localctx, 22, RULE_assignKeys);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(110);
                match(T__12);
                setState(111);
                match(EQ);
                setState(112);
                keyArr();
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class KeyArrContext extends ParserRuleContext {
        public TerminalNode RB() {
            return getToken(HDBTIParser.RB, 0);
        }

        public TerminalNode LB() {
            return getToken(HDBTIParser.LB, 0);
        }

        public List<PairContext> pair() {
            return getRuleContexts(PairContext.class);
        }

        public PairContext pair(int i) {
            return getRuleContext(PairContext.class, i);
        }

        public KeyArrContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_keyArr;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof HDBTIListener)
                ((HDBTIListener) listener).enterKeyArr(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof HDBTIListener)
                ((HDBTIListener) listener).exitKeyArr(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof HDBTIVisitor)
                return ((HDBTIVisitor<? extends T>) visitor).visitKeyArr(this);
            else
                return visitor.visitChildren(this);
        }
    }

    public final KeyArrContext keyArr() throws RecognitionException {
        KeyArrContext _localctx = new KeyArrContext(_ctx, getState());
        enterRule(_localctx, 24, RULE_keyArr);
        int _la;
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(114);
                match(RB);
                setState(123);
                _errHandler.sync(this);
                _la = _input.LA(1);
                if (_la == STRING) {
                    {
                        setState(115);
                        pair();
                        setState(120);
                        _errHandler.sync(this);
                        _la = _input.LA(1);
                        while (_la == T__1) {
                            {
                                {
                                    setState(116);
                                    match(T__1);
                                    setState(117);
                                    pair();
                                }
                            }
                            setState(122);
                            _errHandler.sync(this);
                            _la = _input.LA(1);
                        }
                    }
                }

                setState(125);
                match(LB);
                setState(126);
                match(T__2);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class PairContext extends ParserRuleContext {
        public PairKeyContext pairKey() {
            return getRuleContext(PairKeyContext.class, 0);
        }

        public PairValueContext pairValue() {
            return getRuleContext(PairValueContext.class, 0);
        }

        public PairContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_pair;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof HDBTIListener)
                ((HDBTIListener) listener).enterPair(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof HDBTIListener)
                ((HDBTIListener) listener).exitPair(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof HDBTIVisitor)
                return ((HDBTIVisitor<? extends T>) visitor).visitPair(this);
            else
                return visitor.visitChildren(this);
        }
    }

    public final PairContext pair() throws RecognitionException {
        PairContext _localctx = new PairContext(_ctx, getState());
        enterRule(_localctx, 26, RULE_pair);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(128);
                pairKey();
                setState(129);
                match(T__13);
                setState(130);
                pairValue();
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class PairKeyContext extends ParserRuleContext {
        public TerminalNode STRING() {
            return getToken(HDBTIParser.STRING, 0);
        }

        public PairKeyContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_pairKey;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof HDBTIListener)
                ((HDBTIListener) listener).enterPairKey(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof HDBTIListener)
                ((HDBTIListener) listener).exitPairKey(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof HDBTIVisitor)
                return ((HDBTIVisitor<? extends T>) visitor).visitPairKey(this);
            else
                return visitor.visitChildren(this);
        }
    }

    public final PairKeyContext pairKey() throws RecognitionException {
        PairKeyContext _localctx = new PairKeyContext(_ctx, getState());
        enterRule(_localctx, 28, RULE_pairKey);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(132);
                match(STRING);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class PairValueContext extends ParserRuleContext {
        public TerminalNode STRING() {
            return getToken(HDBTIParser.STRING, 0);
        }

        public PairValueContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_pairValue;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof HDBTIListener)
                ((HDBTIListener) listener).enterPairValue(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof HDBTIListener)
                ((HDBTIListener) listener).exitPairValue(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof HDBTIVisitor)
                return ((HDBTIVisitor<? extends T>) visitor).visitPairValue(this);
            else
                return visitor.visitChildren(this);
        }
    }

    public final PairValueContext pairValue() throws RecognitionException {
        PairValueContext _localctx = new PairValueContext(_ctx, getState());
        enterRule(_localctx, 30, RULE_pairValue);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(134);
                match(STRING);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class TableNameContext extends ParserRuleContext {
        public TableNameContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_tableName;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof HDBTIListener)
                ((HDBTIListener) listener).enterTableName(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof HDBTIListener)
                ((HDBTIListener) listener).exitTableName(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof HDBTIVisitor)
                return ((HDBTIVisitor<? extends T>) visitor).visitTableName(this);
            else
                return visitor.visitChildren(this);
        }
    }

    public final TableNameContext tableName() throws RecognitionException {
        TableNameContext _localctx = new TableNameContext(_ctx, getState());
        enterRule(_localctx, 32, RULE_tableName);
        int _la;
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(136);
                _la = _input.LA(1);
                if (!((((_la) & ~0x3f) == 0 && ((1L << _la) & 229376L) != 0))) {
                    _errHandler.recoverInline(this);
                } else {
                    if (_input.LA(1) == Token.EOF)
                        matchedEOF = true;
                    _errHandler.reportMatch(this);
                    consume();
                }
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public static final String _serializedATN = "\u0004\u0001\u001b\u008b\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001"
            + "\u0002\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004"
            + "\u0002\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007"
            + "\u0002\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b"
            + "\u0002\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002\u000f\u0007"
            + "\u000f\u0002\u0010\u0007\u0010\u0001\u0000\u0001\u0000\u0001\u0000\u0001"
            + "\u0000\u0001\u0000\u0001\u0000\u0005\u0000)\b\u0000\n\u0000\f\u0000,\t"
            + "\u0000\u0003\u0000.\b\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001"
            + "\u0001\u0001\u0001\u0005\u00015\b\u0001\n\u0001\f\u00018\t\u0001\u0001"
            + "\u0001\u0001\u0001\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001"
            + "\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0003\u0002E\b"
            + "\u0002\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001"
            + "\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0005\u0001"
            + "\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0006\u0001\u0006\u0001"
            + "\u0006\u0001\u0006\u0001\u0006\u0001\u0007\u0001\u0007\u0001\u0007\u0001"
            + "\u0007\u0001\u0007\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\t\u0001"
            + "\t\u0001\t\u0001\t\u0001\t\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001"
            + "\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\f\u0001\f\u0001\f\u0001"
            + "\f\u0005\fw\b\f\n\f\f\fz\t\f\u0003\f|\b\f\u0001\f\u0001\f\u0001\f\u0001"
            + "\r\u0001\r\u0001\r\u0001\r\u0001\u000e\u0001\u000e\u0001\u000f\u0001\u000f"
            + "\u0001\u0010\u0001\u0010\u0001\u0010\u0000\u0000\u0011\u0000\u0002\u0004"
            + "\u0006\b\n\f\u000e\u0010\u0012\u0014\u0016\u0018\u001a\u001c\u001e \u0000"
            + "\u0001\u0001\u0000\u000f\u0011\u0086\u0000\"\u0001\u0000\u0000\u0000\u0002"
            + "2\u0001\u0000\u0000\u0000\u0004D\u0001\u0000\u0000\u0000\u0006F\u0001"
            + "\u0000\u0000\u0000\bK\u0001\u0000\u0000\u0000\nP\u0001\u0000\u0000\u0000"
            + "\fU\u0001\u0000\u0000\u0000\u000eZ\u0001\u0000\u0000\u0000\u0010_\u0001"
            + "\u0000\u0000\u0000\u0012d\u0001\u0000\u0000\u0000\u0014i\u0001\u0000\u0000"
            + "\u0000\u0016n\u0001\u0000\u0000\u0000\u0018r\u0001\u0000\u0000\u0000\u001a"
            + "\u0080\u0001\u0000\u0000\u0000\u001c\u0084\u0001\u0000\u0000\u0000\u001e"
            + "\u0086\u0001\u0000\u0000\u0000 \u0088\u0001\u0000\u0000\u0000\"#\u0005"
            + "\u0001\u0000\u0000#$\u0005\u0019\u0000\u0000$-\u0005\u0017\u0000\u0000"
            + "%*\u0003\u0002\u0001\u0000&\'\u0005\u0002\u0000\u0000\')\u0003\u0002\u0001"
            + "\u0000(&\u0001\u0000\u0000\u0000),\u0001\u0000\u0000\u0000*(\u0001\u0000"
            + "\u0000\u0000*+\u0001\u0000\u0000\u0000+.\u0001\u0000\u0000\u0000,*\u0001"
            + "\u0000\u0000\u0000-%\u0001\u0000\u0000\u0000-.\u0001\u0000\u0000\u0000"
            + "./\u0001\u0000\u0000\u0000/0\u0005\u0018\u0000\u000001\u0005\u0003\u0000"
            + "\u00001\u0001\u0001\u0000\u0000\u000026\u0005\u0004\u0000\u000035\u0003"
            + "\u0004\u0002\u000043\u0001\u0000\u0000\u000058\u0001\u0000\u0000\u0000"
            + "64\u0001\u0000\u0000\u000067\u0001\u0000\u0000\u000079\u0001\u0000\u0000"
            + "\u000086\u0001\u0000\u0000\u00009:\u0005\u0005\u0000\u0000:\u0003\u0001"
            + "\u0000\u0000\u0000;E\u0003\u0006\u0003\u0000<E\u0003\b\u0004\u0000=E\u0003"
            + "\n\u0005\u0000>E\u0003\f\u0006\u0000?E\u0003\u000e\u0007\u0000@E\u0003"
            + "\u0010\b\u0000AE\u0003\u0012\t\u0000BE\u0003\u0014\n\u0000CE\u0003\u0016"
            + "\u000b\u0000D;\u0001\u0000\u0000\u0000D<\u0001\u0000\u0000\u0000D=\u0001"
            + "\u0000\u0000\u0000D>\u0001\u0000\u0000\u0000D?\u0001\u0000\u0000\u0000"
            + "D@\u0001\u0000\u0000\u0000DA\u0001\u0000\u0000\u0000DB\u0001\u0000\u0000"
            + "\u0000DC\u0001\u0000\u0000\u0000E\u0005\u0001\u0000\u0000\u0000FG\u0003"
            + " \u0010\u0000GH\u0005\u0019\u0000\u0000HI\u0005\u0012\u0000\u0000IJ\u0005"
            + "\u0003\u0000\u0000J\u0007\u0001\u0000\u0000\u0000KL\u0005\u0006\u0000"
            + "\u0000LM\u0005\u0019\u0000\u0000MN\u0005\u0012\u0000\u0000NO\u0005\u0003"
            + "\u0000\u0000O\t\u0001\u0000\u0000\u0000PQ\u0005\u0007\u0000\u0000QR\u0005"
            + "\u0019\u0000\u0000RS\u0005\u0012\u0000\u0000ST\u0005\u0003\u0000\u0000"
            + "T\u000b\u0001\u0000\u0000\u0000UV\u0005\b\u0000\u0000VW\u0005\u0019\u0000"
            + "\u0000WX\u0005\u0013\u0000\u0000XY\u0005\u0003\u0000\u0000Y\r\u0001\u0000"
            + "\u0000\u0000Z[\u0005\t\u0000\u0000[\\\u0005\u0019\u0000\u0000\\]\u0005"
            + "\u0013\u0000\u0000]^\u0005\u0003\u0000\u0000^\u000f\u0001\u0000\u0000"
            + "\u0000_`\u0005\n\u0000\u0000`a\u0005\u0019\u0000\u0000ab\u0005\u0012\u0000"
            + "\u0000bc\u0005\u0003\u0000\u0000c\u0011\u0001\u0000\u0000\u0000de\u0005"
            + "\u000b\u0000\u0000ef\u0005\u0019\u0000\u0000fg\u0005\u0012\u0000\u0000"
            + "gh\u0005\u0003\u0000\u0000h\u0013\u0001\u0000\u0000\u0000ij\u0005\f\u0000"
            + "\u0000jk\u0005\u0019\u0000\u0000kl\u0005\u0013\u0000\u0000lm\u0005\u0003"
            + "\u0000\u0000m\u0015\u0001\u0000\u0000\u0000no\u0005\r\u0000\u0000op\u0005"
            + "\u0019\u0000\u0000pq\u0003\u0018\f\u0000q\u0017\u0001\u0000\u0000\u0000"
            + "r{\u0005\u0017\u0000\u0000sx\u0003\u001a\r\u0000tu\u0005\u0002\u0000\u0000"
            + "uw\u0003\u001a\r\u0000vt\u0001\u0000\u0000\u0000wz\u0001\u0000\u0000\u0000"
            + "xv\u0001\u0000\u0000\u0000xy\u0001\u0000\u0000\u0000y|\u0001\u0000\u0000"
            + "\u0000zx\u0001\u0000\u0000\u0000{s\u0001\u0000\u0000\u0000{|\u0001\u0000"
            + "\u0000\u0000|}\u0001\u0000\u0000\u0000}~\u0005\u0018\u0000\u0000~\u007f"
            + "\u0005\u0003\u0000\u0000\u007f\u0019\u0001\u0000\u0000\u0000\u0080\u0081"
            + "\u0003\u001c\u000e\u0000\u0081\u0082\u0005\u000e\u0000\u0000\u0082\u0083"
            + "\u0003\u001e\u000f\u0000\u0083\u001b\u0001\u0000\u0000\u0000\u0084\u0085"
            + "\u0005\u0012\u0000\u0000\u0085\u001d\u0001\u0000\u0000\u0000\u0086\u0087"
            + "\u0005\u0012\u0000\u0000\u0087\u001f\u0001\u0000\u0000\u0000\u0088\u0089"
            + "\u0007\u0000\u0000\u0000\u0089!\u0001\u0000\u0000\u0000\u0006*-6Dx{";
    public static final ATN _ATN = new ATNDeserializer().deserialize(_serializedATN.toCharArray());
    static {
        _decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
        for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
            _decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
        }
    }
}
