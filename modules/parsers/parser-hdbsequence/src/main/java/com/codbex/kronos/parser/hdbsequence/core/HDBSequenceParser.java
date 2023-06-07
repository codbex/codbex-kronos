/*
 * Copyright (c) 2022 codbex or an codbex affiliate company and contributors
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-FileCopyrightText: 2022 codbex or an codbex affiliate company and contributors
 * SPDX-License-Identifier: EPL-2.0
 */
// Generated from java-escape by ANTLR 4.11.1
package com.codbex.kronos.parser.hdbsequence.core;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue"})
public class HDBSequenceParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.11.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, STRING=13, INT=14, BOOLEAN=15, TRUE=16, FALSE=17, 
		WS=18, LB=19, RB=20, EQ=21, SC=22, SIGNED_INT=23, LINE_COMMENT=24, COMMENT=25;
	public static final int
		RULE_hdbSequenceDefinition = 0, RULE_property = 1, RULE_schema = 2, RULE_incrementBy = 3, 
		RULE_startWith = 4, RULE_maxValue = 5, RULE_noMaxValue = 6, RULE_minValue = 7, 
		RULE_noMinValue = 8, RULE_cycles = 9, RULE_resetBy = 10, RULE_publicc = 11, 
		RULE_dependsOnTable = 12, RULE_dependsOnView = 13;
	private static String[] makeRuleNames() {
		return new String[] {
			"hdbSequenceDefinition", "property", "schema", "incrementBy", "startWith", 
			"maxValue", "noMaxValue", "minValue", "noMinValue", "cycles", "resetBy", 
			"publicc", "dependsOnTable", "dependsOnView"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'schema'", "'increment_by'", "'start_with'", "'maxvalue'", "'nomaxvalue'", 
			"'minvalue'", "'nominvalue'", "'cycles'", "'reset_by'", "'public'", "'depends_on_table'", 
			"'depends_on_view'", null, null, null, "'true'", "'false'", null, "'['", 
			"']'", "'='", "';'", "'-'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, "STRING", "INT", "BOOLEAN", "TRUE", "FALSE", "WS", "LB", "RB", 
			"EQ", "SC", "SIGNED_INT", "LINE_COMMENT", "COMMENT"
		};
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
	public String getGrammarFileName() { return "java-escape"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public HDBSequenceParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class HdbSequenceDefinitionContext extends ParserRuleContext {
		public List<PropertyContext> property() {
			return getRuleContexts(PropertyContext.class);
		}
		public PropertyContext property(int i) {
			return getRuleContext(PropertyContext.class,i);
		}
		public HdbSequenceDefinitionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_hdbSequenceDefinition; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof HDBSequenceListener ) ((HDBSequenceListener)listener).enterHdbSequenceDefinition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof HDBSequenceListener ) ((HDBSequenceListener)listener).exitHdbSequenceDefinition(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HDBSequenceVisitor ) return ((HDBSequenceVisitor<? extends T>)visitor).visitHdbSequenceDefinition(this);
			else return visitor.visitChildren(this);
		}
	}

	public final HdbSequenceDefinitionContext hdbSequenceDefinition() throws RecognitionException {
		HdbSequenceDefinitionContext _localctx = new HdbSequenceDefinitionContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_hdbSequenceDefinition);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(29); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(28);
				property();
				}
				}
				setState(31); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( ((_la) & ~0x3f) == 0 && ((1L << _la) & 8190L) != 0 );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class PropertyContext extends ParserRuleContext {
		public SchemaContext schema() {
			return getRuleContext(SchemaContext.class,0);
		}
		public IncrementByContext incrementBy() {
			return getRuleContext(IncrementByContext.class,0);
		}
		public StartWithContext startWith() {
			return getRuleContext(StartWithContext.class,0);
		}
		public MaxValueContext maxValue() {
			return getRuleContext(MaxValueContext.class,0);
		}
		public NoMaxValueContext noMaxValue() {
			return getRuleContext(NoMaxValueContext.class,0);
		}
		public MinValueContext minValue() {
			return getRuleContext(MinValueContext.class,0);
		}
		public NoMinValueContext noMinValue() {
			return getRuleContext(NoMinValueContext.class,0);
		}
		public CyclesContext cycles() {
			return getRuleContext(CyclesContext.class,0);
		}
		public ResetByContext resetBy() {
			return getRuleContext(ResetByContext.class,0);
		}
		public PubliccContext publicc() {
			return getRuleContext(PubliccContext.class,0);
		}
		public DependsOnTableContext dependsOnTable() {
			return getRuleContext(DependsOnTableContext.class,0);
		}
		public DependsOnViewContext dependsOnView() {
			return getRuleContext(DependsOnViewContext.class,0);
		}
		public PropertyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_property; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof HDBSequenceListener ) ((HDBSequenceListener)listener).enterProperty(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof HDBSequenceListener ) ((HDBSequenceListener)listener).exitProperty(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HDBSequenceVisitor ) return ((HDBSequenceVisitor<? extends T>)visitor).visitProperty(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PropertyContext property() throws RecognitionException {
		PropertyContext _localctx = new PropertyContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_property);
		try {
			setState(45);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__0:
				enterOuterAlt(_localctx, 1);
				{
				setState(33);
				schema();
				}
				break;
			case T__1:
				enterOuterAlt(_localctx, 2);
				{
				setState(34);
				incrementBy();
				}
				break;
			case T__2:
				enterOuterAlt(_localctx, 3);
				{
				setState(35);
				startWith();
				}
				break;
			case T__3:
				enterOuterAlt(_localctx, 4);
				{
				setState(36);
				maxValue();
				}
				break;
			case T__4:
				enterOuterAlt(_localctx, 5);
				{
				setState(37);
				noMaxValue();
				}
				break;
			case T__5:
				enterOuterAlt(_localctx, 6);
				{
				setState(38);
				minValue();
				}
				break;
			case T__6:
				enterOuterAlt(_localctx, 7);
				{
				setState(39);
				noMinValue();
				}
				break;
			case T__7:
				enterOuterAlt(_localctx, 8);
				{
				setState(40);
				cycles();
				}
				break;
			case T__8:
				enterOuterAlt(_localctx, 9);
				{
				setState(41);
				resetBy();
				}
				break;
			case T__9:
				enterOuterAlt(_localctx, 10);
				{
				setState(42);
				publicc();
				}
				break;
			case T__10:
				enterOuterAlt(_localctx, 11);
				{
				setState(43);
				dependsOnTable();
				}
				break;
			case T__11:
				enterOuterAlt(_localctx, 12);
				{
				setState(44);
				dependsOnView();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class SchemaContext extends ParserRuleContext {
		public TerminalNode EQ() { return getToken(HDBSequenceParser.EQ, 0); }
		public TerminalNode STRING() { return getToken(HDBSequenceParser.STRING, 0); }
		public TerminalNode SC() { return getToken(HDBSequenceParser.SC, 0); }
		public SchemaContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_schema; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof HDBSequenceListener ) ((HDBSequenceListener)listener).enterSchema(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof HDBSequenceListener ) ((HDBSequenceListener)listener).exitSchema(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HDBSequenceVisitor ) return ((HDBSequenceVisitor<? extends T>)visitor).visitSchema(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SchemaContext schema() throws RecognitionException {
		SchemaContext _localctx = new SchemaContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_schema);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(47);
			match(T__0);
			setState(48);
			match(EQ);
			setState(49);
			match(STRING);
			setState(50);
			match(SC);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class IncrementByContext extends ParserRuleContext {
		public TerminalNode EQ() { return getToken(HDBSequenceParser.EQ, 0); }
		public TerminalNode INT() { return getToken(HDBSequenceParser.INT, 0); }
		public TerminalNode SC() { return getToken(HDBSequenceParser.SC, 0); }
		public IncrementByContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_incrementBy; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof HDBSequenceListener ) ((HDBSequenceListener)listener).enterIncrementBy(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof HDBSequenceListener ) ((HDBSequenceListener)listener).exitIncrementBy(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HDBSequenceVisitor ) return ((HDBSequenceVisitor<? extends T>)visitor).visitIncrementBy(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IncrementByContext incrementBy() throws RecognitionException {
		IncrementByContext _localctx = new IncrementByContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_incrementBy);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(52);
			match(T__1);
			setState(53);
			match(EQ);
			setState(54);
			match(INT);
			setState(55);
			match(SC);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class StartWithContext extends ParserRuleContext {
		public TerminalNode EQ() { return getToken(HDBSequenceParser.EQ, 0); }
		public TerminalNode INT() { return getToken(HDBSequenceParser.INT, 0); }
		public TerminalNode SC() { return getToken(HDBSequenceParser.SC, 0); }
		public StartWithContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_startWith; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof HDBSequenceListener ) ((HDBSequenceListener)listener).enterStartWith(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof HDBSequenceListener ) ((HDBSequenceListener)listener).exitStartWith(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HDBSequenceVisitor ) return ((HDBSequenceVisitor<? extends T>)visitor).visitStartWith(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StartWithContext startWith() throws RecognitionException {
		StartWithContext _localctx = new StartWithContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_startWith);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(57);
			match(T__2);
			setState(58);
			match(EQ);
			setState(59);
			match(INT);
			setState(60);
			match(SC);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class MaxValueContext extends ParserRuleContext {
		public TerminalNode EQ() { return getToken(HDBSequenceParser.EQ, 0); }
		public TerminalNode INT() { return getToken(HDBSequenceParser.INT, 0); }
		public TerminalNode SC() { return getToken(HDBSequenceParser.SC, 0); }
		public MaxValueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_maxValue; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof HDBSequenceListener ) ((HDBSequenceListener)listener).enterMaxValue(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof HDBSequenceListener ) ((HDBSequenceListener)listener).exitMaxValue(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HDBSequenceVisitor ) return ((HDBSequenceVisitor<? extends T>)visitor).visitMaxValue(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MaxValueContext maxValue() throws RecognitionException {
		MaxValueContext _localctx = new MaxValueContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_maxValue);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(62);
			match(T__3);
			setState(63);
			match(EQ);
			setState(64);
			match(INT);
			setState(65);
			match(SC);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class NoMaxValueContext extends ParserRuleContext {
		public TerminalNode EQ() { return getToken(HDBSequenceParser.EQ, 0); }
		public TerminalNode BOOLEAN() { return getToken(HDBSequenceParser.BOOLEAN, 0); }
		public TerminalNode SC() { return getToken(HDBSequenceParser.SC, 0); }
		public NoMaxValueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_noMaxValue; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof HDBSequenceListener ) ((HDBSequenceListener)listener).enterNoMaxValue(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof HDBSequenceListener ) ((HDBSequenceListener)listener).exitNoMaxValue(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HDBSequenceVisitor ) return ((HDBSequenceVisitor<? extends T>)visitor).visitNoMaxValue(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NoMaxValueContext noMaxValue() throws RecognitionException {
		NoMaxValueContext _localctx = new NoMaxValueContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_noMaxValue);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(67);
			match(T__4);
			setState(68);
			match(EQ);
			setState(69);
			match(BOOLEAN);
			setState(70);
			match(SC);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class MinValueContext extends ParserRuleContext {
		public TerminalNode EQ() { return getToken(HDBSequenceParser.EQ, 0); }
		public TerminalNode INT() { return getToken(HDBSequenceParser.INT, 0); }
		public TerminalNode SC() { return getToken(HDBSequenceParser.SC, 0); }
		public MinValueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_minValue; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof HDBSequenceListener ) ((HDBSequenceListener)listener).enterMinValue(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof HDBSequenceListener ) ((HDBSequenceListener)listener).exitMinValue(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HDBSequenceVisitor ) return ((HDBSequenceVisitor<? extends T>)visitor).visitMinValue(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MinValueContext minValue() throws RecognitionException {
		MinValueContext _localctx = new MinValueContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_minValue);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(72);
			match(T__5);
			setState(73);
			match(EQ);
			setState(74);
			match(INT);
			setState(75);
			match(SC);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class NoMinValueContext extends ParserRuleContext {
		public TerminalNode EQ() { return getToken(HDBSequenceParser.EQ, 0); }
		public TerminalNode BOOLEAN() { return getToken(HDBSequenceParser.BOOLEAN, 0); }
		public TerminalNode SC() { return getToken(HDBSequenceParser.SC, 0); }
		public NoMinValueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_noMinValue; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof HDBSequenceListener ) ((HDBSequenceListener)listener).enterNoMinValue(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof HDBSequenceListener ) ((HDBSequenceListener)listener).exitNoMinValue(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HDBSequenceVisitor ) return ((HDBSequenceVisitor<? extends T>)visitor).visitNoMinValue(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NoMinValueContext noMinValue() throws RecognitionException {
		NoMinValueContext _localctx = new NoMinValueContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_noMinValue);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(77);
			match(T__6);
			setState(78);
			match(EQ);
			setState(79);
			match(BOOLEAN);
			setState(80);
			match(SC);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class CyclesContext extends ParserRuleContext {
		public TerminalNode EQ() { return getToken(HDBSequenceParser.EQ, 0); }
		public TerminalNode BOOLEAN() { return getToken(HDBSequenceParser.BOOLEAN, 0); }
		public TerminalNode SC() { return getToken(HDBSequenceParser.SC, 0); }
		public CyclesContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_cycles; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof HDBSequenceListener ) ((HDBSequenceListener)listener).enterCycles(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof HDBSequenceListener ) ((HDBSequenceListener)listener).exitCycles(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HDBSequenceVisitor ) return ((HDBSequenceVisitor<? extends T>)visitor).visitCycles(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CyclesContext cycles() throws RecognitionException {
		CyclesContext _localctx = new CyclesContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_cycles);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(82);
			match(T__7);
			setState(83);
			match(EQ);
			setState(84);
			match(BOOLEAN);
			setState(85);
			match(SC);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ResetByContext extends ParserRuleContext {
		public TerminalNode EQ() { return getToken(HDBSequenceParser.EQ, 0); }
		public TerminalNode STRING() { return getToken(HDBSequenceParser.STRING, 0); }
		public TerminalNode SC() { return getToken(HDBSequenceParser.SC, 0); }
		public ResetByContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_resetBy; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof HDBSequenceListener ) ((HDBSequenceListener)listener).enterResetBy(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof HDBSequenceListener ) ((HDBSequenceListener)listener).exitResetBy(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HDBSequenceVisitor ) return ((HDBSequenceVisitor<? extends T>)visitor).visitResetBy(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ResetByContext resetBy() throws RecognitionException {
		ResetByContext _localctx = new ResetByContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_resetBy);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(87);
			match(T__8);
			setState(88);
			match(EQ);
			setState(89);
			match(STRING);
			setState(90);
			match(SC);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class PubliccContext extends ParserRuleContext {
		public TerminalNode EQ() { return getToken(HDBSequenceParser.EQ, 0); }
		public TerminalNode BOOLEAN() { return getToken(HDBSequenceParser.BOOLEAN, 0); }
		public TerminalNode SC() { return getToken(HDBSequenceParser.SC, 0); }
		public PubliccContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_publicc; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof HDBSequenceListener ) ((HDBSequenceListener)listener).enterPublicc(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof HDBSequenceListener ) ((HDBSequenceListener)listener).exitPublicc(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HDBSequenceVisitor ) return ((HDBSequenceVisitor<? extends T>)visitor).visitPublicc(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PubliccContext publicc() throws RecognitionException {
		PubliccContext _localctx = new PubliccContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_publicc);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(92);
			match(T__9);
			setState(93);
			match(EQ);
			setState(94);
			match(BOOLEAN);
			setState(95);
			match(SC);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class DependsOnTableContext extends ParserRuleContext {
		public TerminalNode EQ() { return getToken(HDBSequenceParser.EQ, 0); }
		public TerminalNode STRING() { return getToken(HDBSequenceParser.STRING, 0); }
		public TerminalNode SC() { return getToken(HDBSequenceParser.SC, 0); }
		public DependsOnTableContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_dependsOnTable; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof HDBSequenceListener ) ((HDBSequenceListener)listener).enterDependsOnTable(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof HDBSequenceListener ) ((HDBSequenceListener)listener).exitDependsOnTable(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HDBSequenceVisitor ) return ((HDBSequenceVisitor<? extends T>)visitor).visitDependsOnTable(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DependsOnTableContext dependsOnTable() throws RecognitionException {
		DependsOnTableContext _localctx = new DependsOnTableContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_dependsOnTable);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(97);
			match(T__10);
			setState(98);
			match(EQ);
			setState(99);
			match(STRING);
			setState(100);
			match(SC);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class DependsOnViewContext extends ParserRuleContext {
		public TerminalNode EQ() { return getToken(HDBSequenceParser.EQ, 0); }
		public TerminalNode STRING() { return getToken(HDBSequenceParser.STRING, 0); }
		public TerminalNode SC() { return getToken(HDBSequenceParser.SC, 0); }
		public DependsOnViewContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_dependsOnView; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof HDBSequenceListener ) ((HDBSequenceListener)listener).enterDependsOnView(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof HDBSequenceListener ) ((HDBSequenceListener)listener).exitDependsOnView(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HDBSequenceVisitor ) return ((HDBSequenceVisitor<? extends T>)visitor).visitDependsOnView(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DependsOnViewContext dependsOnView() throws RecognitionException {
		DependsOnViewContext _localctx = new DependsOnViewContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_dependsOnView);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(102);
			match(T__11);
			setState(103);
			match(EQ);
			setState(104);
			match(STRING);
			setState(105);
			match(SC);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\u0004\u0001\u0019l\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"+
		"\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002"+
		"\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b\u0002"+
		"\f\u0007\f\u0002\r\u0007\r\u0001\u0000\u0004\u0000\u001e\b\u0000\u000b"+
		"\u0000\f\u0000\u001f\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0003\u0001.\b\u0001\u0001\u0002\u0001\u0002\u0001"+
		"\u0002\u0001\u0002\u0001\u0002\u0001\u0003\u0001\u0003\u0001\u0003\u0001"+
		"\u0003\u0001\u0003\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001"+
		"\u0004\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001"+
		"\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0007\u0001"+
		"\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\b\u0001\b\u0001\b\u0001"+
		"\b\u0001\b\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\n\u0001\n\u0001"+
		"\n\u0001\n\u0001\n\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001"+
		"\u000b\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\r\u0001\r\u0001\r"+
		"\u0001\r\u0001\r\u0001\r\u0000\u0000\u000e\u0000\u0002\u0004\u0006\b\n"+
		"\f\u000e\u0010\u0012\u0014\u0016\u0018\u001a\u0000\u0000i\u0000\u001d"+
		"\u0001\u0000\u0000\u0000\u0002-\u0001\u0000\u0000\u0000\u0004/\u0001\u0000"+
		"\u0000\u0000\u00064\u0001\u0000\u0000\u0000\b9\u0001\u0000\u0000\u0000"+
		"\n>\u0001\u0000\u0000\u0000\fC\u0001\u0000\u0000\u0000\u000eH\u0001\u0000"+
		"\u0000\u0000\u0010M\u0001\u0000\u0000\u0000\u0012R\u0001\u0000\u0000\u0000"+
		"\u0014W\u0001\u0000\u0000\u0000\u0016\\\u0001\u0000\u0000\u0000\u0018"+
		"a\u0001\u0000\u0000\u0000\u001af\u0001\u0000\u0000\u0000\u001c\u001e\u0003"+
		"\u0002\u0001\u0000\u001d\u001c\u0001\u0000\u0000\u0000\u001e\u001f\u0001"+
		"\u0000\u0000\u0000\u001f\u001d\u0001\u0000\u0000\u0000\u001f \u0001\u0000"+
		"\u0000\u0000 \u0001\u0001\u0000\u0000\u0000!.\u0003\u0004\u0002\u0000"+
		"\".\u0003\u0006\u0003\u0000#.\u0003\b\u0004\u0000$.\u0003\n\u0005\u0000"+
		"%.\u0003\f\u0006\u0000&.\u0003\u000e\u0007\u0000\'.\u0003\u0010\b\u0000"+
		"(.\u0003\u0012\t\u0000).\u0003\u0014\n\u0000*.\u0003\u0016\u000b\u0000"+
		"+.\u0003\u0018\f\u0000,.\u0003\u001a\r\u0000-!\u0001\u0000\u0000\u0000"+
		"-\"\u0001\u0000\u0000\u0000-#\u0001\u0000\u0000\u0000-$\u0001\u0000\u0000"+
		"\u0000-%\u0001\u0000\u0000\u0000-&\u0001\u0000\u0000\u0000-\'\u0001\u0000"+
		"\u0000\u0000-(\u0001\u0000\u0000\u0000-)\u0001\u0000\u0000\u0000-*\u0001"+
		"\u0000\u0000\u0000-+\u0001\u0000\u0000\u0000-,\u0001\u0000\u0000\u0000"+
		".\u0003\u0001\u0000\u0000\u0000/0\u0005\u0001\u0000\u000001\u0005\u0015"+
		"\u0000\u000012\u0005\r\u0000\u000023\u0005\u0016\u0000\u00003\u0005\u0001"+
		"\u0000\u0000\u000045\u0005\u0002\u0000\u000056\u0005\u0015\u0000\u0000"+
		"67\u0005\u000e\u0000\u000078\u0005\u0016\u0000\u00008\u0007\u0001\u0000"+
		"\u0000\u00009:\u0005\u0003\u0000\u0000:;\u0005\u0015\u0000\u0000;<\u0005"+
		"\u000e\u0000\u0000<=\u0005\u0016\u0000\u0000=\t\u0001\u0000\u0000\u0000"+
		">?\u0005\u0004\u0000\u0000?@\u0005\u0015\u0000\u0000@A\u0005\u000e\u0000"+
		"\u0000AB\u0005\u0016\u0000\u0000B\u000b\u0001\u0000\u0000\u0000CD\u0005"+
		"\u0005\u0000\u0000DE\u0005\u0015\u0000\u0000EF\u0005\u000f\u0000\u0000"+
		"FG\u0005\u0016\u0000\u0000G\r\u0001\u0000\u0000\u0000HI\u0005\u0006\u0000"+
		"\u0000IJ\u0005\u0015\u0000\u0000JK\u0005\u000e\u0000\u0000KL\u0005\u0016"+
		"\u0000\u0000L\u000f\u0001\u0000\u0000\u0000MN\u0005\u0007\u0000\u0000"+
		"NO\u0005\u0015\u0000\u0000OP\u0005\u000f\u0000\u0000PQ\u0005\u0016\u0000"+
		"\u0000Q\u0011\u0001\u0000\u0000\u0000RS\u0005\b\u0000\u0000ST\u0005\u0015"+
		"\u0000\u0000TU\u0005\u000f\u0000\u0000UV\u0005\u0016\u0000\u0000V\u0013"+
		"\u0001\u0000\u0000\u0000WX\u0005\t\u0000\u0000XY\u0005\u0015\u0000\u0000"+
		"YZ\u0005\r\u0000\u0000Z[\u0005\u0016\u0000\u0000[\u0015\u0001\u0000\u0000"+
		"\u0000\\]\u0005\n\u0000\u0000]^\u0005\u0015\u0000\u0000^_\u0005\u000f"+
		"\u0000\u0000_`\u0005\u0016\u0000\u0000`\u0017\u0001\u0000\u0000\u0000"+
		"ab\u0005\u000b\u0000\u0000bc\u0005\u0015\u0000\u0000cd\u0005\r\u0000\u0000"+
		"de\u0005\u0016\u0000\u0000e\u0019\u0001\u0000\u0000\u0000fg\u0005\f\u0000"+
		"\u0000gh\u0005\u0015\u0000\u0000hi\u0005\r\u0000\u0000ij\u0005\u0016\u0000"+
		"\u0000j\u001b\u0001\u0000\u0000\u0000\u0002\u001f-";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}