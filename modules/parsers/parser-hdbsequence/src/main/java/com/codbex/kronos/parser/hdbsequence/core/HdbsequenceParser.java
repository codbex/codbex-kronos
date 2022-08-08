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
// Generated from com/codbex/kronos/parser/hdbsequence/core/Hdbsequence.g4 by ANTLR 4.10.1
package com.codbex.kronos.parser.hdbsequence.core;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

/**
 * The Class HdbsequenceParser.
 */
@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class HdbsequenceParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.10.1", RuntimeMetaData.VERSION); }

	/** The Constant _decisionToDFA. */
	protected static final DFA[] _decisionToDFA;
	
	/** The Constant _sharedContextCache. */
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	
	/** The Constant COMMENT. */
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, STRING=13, INT=14, BOOLEAN=15, TRUE=16, FALSE=17, 
		WS=18, LB=19, RB=20, EQ=21, SC=22, SIGNED_INT=23, LINE_COMMENT=24, COMMENT=25;
	
	/** The Constant RULE_dependsOnView. */
	public static final int
		RULE_hdbsequence = 0, RULE_property = 1, RULE_schema = 2, RULE_increment_by = 3, 
		RULE_start_with = 4, RULE_maxvalue = 5, RULE_nomaxvalue = 6, RULE_minvalue = 7, 
		RULE_nominvalue = 8, RULE_cycles = 9, RULE_reset_by = 10, RULE_publicc = 11, 
		RULE_dependsOnTable = 12, RULE_dependsOnView = 13;
	
	/**
	 * Make rule names.
	 *
	 * @return the string[]
	 */
	private static String[] makeRuleNames() {
		return new String[] {
			"hdbsequence", "property", "schema", "increment_by", "start_with", "maxvalue", 
			"nomaxvalue", "minvalue", "nominvalue", "cycles", "reset_by", "publicc", 
			"dependsOnTable", "dependsOnView"
		};
	}
	
	/** The Constant ruleNames. */
	public static final String[] ruleNames = makeRuleNames();

	/**
	 * Make literal names.
	 *
	 * @return the string[]
	 */
	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'schema'", "'increment_by'", "'start_with'", "'maxvalue'", "'nomaxvalue'", 
			"'minvalue'", "'nominvalue'", "'cycles'", "'reset_by'", "'public'", "'depends_on_table'", 
			"'depends_on_view'", null, null, null, "'true'", "'false'", null, "'['", 
			"']'", "'='", "';'", "'-'"
		};
	}
	
	/** The Constant _LITERAL_NAMES. */
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	
	/**
	 * Make symbolic names.
	 *
	 * @return the string[]
	 */
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, "STRING", "INT", "BOOLEAN", "TRUE", "FALSE", "WS", "LB", "RB", 
			"EQ", "SC", "SIGNED_INT", "LINE_COMMENT", "COMMENT"
		};
	}
	
	/** The Constant _SYMBOLIC_NAMES. */
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	
	/** The Constant VOCABULARY. */
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * The Constant tokenNames.
	 *
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

	/**
	 * Gets the token names.
	 *
	 * @return the token names
	 */
	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	/**
	 * Gets the vocabulary.
	 *
	 * @return the vocabulary
	 */
	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	/**
	 * Gets the grammar file name.
	 *
	 * @return the grammar file name
	 */
	@Override
	public String getGrammarFileName() { return "Hdbsequence.g4"; }

	/**
	 * Gets the rule names.
	 *
	 * @return the rule names
	 */
	@Override
	public String[] getRuleNames() { return ruleNames; }

	/**
	 * Gets the serialized ATN.
	 *
	 * @return the serialized ATN
	 */
	@Override
	public String getSerializedATN() { return _serializedATN; }

	/**
	 * Gets the atn.
	 *
	 * @return the atn
	 */
	@Override
	public ATN getATN() { return _ATN; }

	/**
	 * Instantiates a new hdbsequence parser.
	 *
	 * @param input the input
	 */
	public HdbsequenceParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	/**
	 * The Class HdbsequenceContext.
	 */
	public static class HdbsequenceContext extends ParserRuleContext {
		
		/**
		 * Property.
		 *
		 * @return the list
		 */
		public List<PropertyContext> property() {
			return getRuleContexts(PropertyContext.class);
		}
		
		/**
		 * Property.
		 *
		 * @param i the i
		 * @return the property context
		 */
		public PropertyContext property(int i) {
			return getRuleContext(PropertyContext.class,i);
		}
		
		/**
		 * Instantiates a new hdbsequence context.
		 *
		 * @param parent the parent
		 * @param invokingState the invoking state
		 */
		public HdbsequenceContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		
		/**
		 * Gets the rule index.
		 *
		 * @return the rule index
		 */
		@Override public int getRuleIndex() { return RULE_hdbsequence; }
		
		/**
		 * Enter rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof HdbsequenceListener ) ((HdbsequenceListener)listener).enterHdbsequence(this);
		}
		
		/**
		 * Exit rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof HdbsequenceListener ) ((HdbsequenceListener)listener).exitHdbsequence(this);
		}
		
		/**
		 * Accept.
		 *
		 * @param <T> the generic type
		 * @param visitor the visitor
		 * @return the t
		 */
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HdbsequenceVisitor ) return ((HdbsequenceVisitor<? extends T>)visitor).visitHdbsequence(this);
			else return visitor.visitChildren(this);
		}
	}

	/**
	 * Hdbsequence.
	 *
	 * @return the hdbsequence context
	 * @throws RecognitionException the recognition exception
	 */
	public final HdbsequenceContext hdbsequence() throws RecognitionException {
		HdbsequenceContext _localctx = new HdbsequenceContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_hdbsequence);
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
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << T__1) | (1L << T__2) | (1L << T__3) | (1L << T__4) | (1L << T__5) | (1L << T__6) | (1L << T__7) | (1L << T__8) | (1L << T__9) | (1L << T__10) | (1L << T__11))) != 0) );
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

	/**
	 * The Class PropertyContext.
	 */
	public static class PropertyContext extends ParserRuleContext {
		
		/**
		 * Schema.
		 *
		 * @return the schema context
		 */
		public SchemaContext schema() {
			return getRuleContext(SchemaContext.class,0);
		}
		
		/**
		 * Increment by.
		 *
		 * @return the increment by context
		 */
		public Increment_byContext increment_by() {
			return getRuleContext(Increment_byContext.class,0);
		}
		
		/**
		 * Start with.
		 *
		 * @return the start with context
		 */
		public Start_withContext start_with() {
			return getRuleContext(Start_withContext.class,0);
		}
		
		/**
		 * Maxvalue.
		 *
		 * @return the maxvalue context
		 */
		public MaxvalueContext maxvalue() {
			return getRuleContext(MaxvalueContext.class,0);
		}
		
		/**
		 * Nomaxvalue.
		 *
		 * @return the nomaxvalue context
		 */
		public NomaxvalueContext nomaxvalue() {
			return getRuleContext(NomaxvalueContext.class,0);
		}
		
		/**
		 * Minvalue.
		 *
		 * @return the minvalue context
		 */
		public MinvalueContext minvalue() {
			return getRuleContext(MinvalueContext.class,0);
		}
		
		/**
		 * Nominvalue.
		 *
		 * @return the nominvalue context
		 */
		public NominvalueContext nominvalue() {
			return getRuleContext(NominvalueContext.class,0);
		}
		
		/**
		 * Cycles.
		 *
		 * @return the cycles context
		 */
		public CyclesContext cycles() {
			return getRuleContext(CyclesContext.class,0);
		}
		
		/**
		 * Reset by.
		 *
		 * @return the reset by context
		 */
		public Reset_byContext reset_by() {
			return getRuleContext(Reset_byContext.class,0);
		}
		
		/**
		 * Publicc.
		 *
		 * @return the publicc context
		 */
		public PubliccContext publicc() {
			return getRuleContext(PubliccContext.class,0);
		}
		
		/**
		 * Depends on table.
		 *
		 * @return the depends on table context
		 */
		public DependsOnTableContext dependsOnTable() {
			return getRuleContext(DependsOnTableContext.class,0);
		}
		
		/**
		 * Depends on view.
		 *
		 * @return the depends on view context
		 */
		public DependsOnViewContext dependsOnView() {
			return getRuleContext(DependsOnViewContext.class,0);
		}
		
		/**
		 * Instantiates a new property context.
		 *
		 * @param parent the parent
		 * @param invokingState the invoking state
		 */
		public PropertyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		
		/**
		 * Gets the rule index.
		 *
		 * @return the rule index
		 */
		@Override public int getRuleIndex() { return RULE_property; }
		
		/**
		 * Enter rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof HdbsequenceListener ) ((HdbsequenceListener)listener).enterProperty(this);
		}
		
		/**
		 * Exit rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof HdbsequenceListener ) ((HdbsequenceListener)listener).exitProperty(this);
		}
		
		/**
		 * Accept.
		 *
		 * @param <T> the generic type
		 * @param visitor the visitor
		 * @return the t
		 */
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HdbsequenceVisitor ) return ((HdbsequenceVisitor<? extends T>)visitor).visitProperty(this);
			else return visitor.visitChildren(this);
		}
	}

	/**
	 * Property.
	 *
	 * @return the property context
	 * @throws RecognitionException the recognition exception
	 */
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
				increment_by();
				}
				break;
			case T__2:
				enterOuterAlt(_localctx, 3);
				{
				setState(35);
				start_with();
				}
				break;
			case T__3:
				enterOuterAlt(_localctx, 4);
				{
				setState(36);
				maxvalue();
				}
				break;
			case T__4:
				enterOuterAlt(_localctx, 5);
				{
				setState(37);
				nomaxvalue();
				}
				break;
			case T__5:
				enterOuterAlt(_localctx, 6);
				{
				setState(38);
				minvalue();
				}
				break;
			case T__6:
				enterOuterAlt(_localctx, 7);
				{
				setState(39);
				nominvalue();
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
				reset_by();
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

	/**
	 * The Class SchemaContext.
	 */
	public static class SchemaContext extends ParserRuleContext {
		
		/**
		 * Eq.
		 *
		 * @return the terminal node
		 */
		public TerminalNode EQ() { return getToken(HdbsequenceParser.EQ, 0); }
		
		/**
		 * String.
		 *
		 * @return the terminal node
		 */
		public TerminalNode STRING() { return getToken(HdbsequenceParser.STRING, 0); }
		
		/**
		 * Sc.
		 *
		 * @return the terminal node
		 */
		public TerminalNode SC() { return getToken(HdbsequenceParser.SC, 0); }
		
		/**
		 * Instantiates a new schema context.
		 *
		 * @param parent the parent
		 * @param invokingState the invoking state
		 */
		public SchemaContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		
		/**
		 * Gets the rule index.
		 *
		 * @return the rule index
		 */
		@Override public int getRuleIndex() { return RULE_schema; }
		
		/**
		 * Enter rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof HdbsequenceListener ) ((HdbsequenceListener)listener).enterSchema(this);
		}
		
		/**
		 * Exit rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof HdbsequenceListener ) ((HdbsequenceListener)listener).exitSchema(this);
		}
		
		/**
		 * Accept.
		 *
		 * @param <T> the generic type
		 * @param visitor the visitor
		 * @return the t
		 */
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HdbsequenceVisitor ) return ((HdbsequenceVisitor<? extends T>)visitor).visitSchema(this);
			else return visitor.visitChildren(this);
		}
	}

	/**
	 * Schema.
	 *
	 * @return the schema context
	 * @throws RecognitionException the recognition exception
	 */
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

	/**
	 * The Class Increment_byContext.
	 */
	public static class Increment_byContext extends ParserRuleContext {
		
		/**
		 * Eq.
		 *
		 * @return the terminal node
		 */
		public TerminalNode EQ() { return getToken(HdbsequenceParser.EQ, 0); }
		
		/**
		 * Int.
		 *
		 * @return the terminal node
		 */
		public TerminalNode INT() { return getToken(HdbsequenceParser.INT, 0); }
		
		/**
		 * Sc.
		 *
		 * @return the terminal node
		 */
		public TerminalNode SC() { return getToken(HdbsequenceParser.SC, 0); }
		
		/**
		 * Instantiates a new increment by context.
		 *
		 * @param parent the parent
		 * @param invokingState the invoking state
		 */
		public Increment_byContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		
		/**
		 * Gets the rule index.
		 *
		 * @return the rule index
		 */
		@Override public int getRuleIndex() { return RULE_increment_by; }
		
		/**
		 * Enter rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof HdbsequenceListener ) ((HdbsequenceListener)listener).enterIncrement_by(this);
		}
		
		/**
		 * Exit rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof HdbsequenceListener ) ((HdbsequenceListener)listener).exitIncrement_by(this);
		}
		
		/**
		 * Accept.
		 *
		 * @param <T> the generic type
		 * @param visitor the visitor
		 * @return the t
		 */
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HdbsequenceVisitor ) return ((HdbsequenceVisitor<? extends T>)visitor).visitIncrement_by(this);
			else return visitor.visitChildren(this);
		}
	}

	/**
	 * Increment by.
	 *
	 * @return the increment by context
	 * @throws RecognitionException the recognition exception
	 */
	public final Increment_byContext increment_by() throws RecognitionException {
		Increment_byContext _localctx = new Increment_byContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_increment_by);
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

	/**
	 * The Class Start_withContext.
	 */
	public static class Start_withContext extends ParserRuleContext {
		
		/**
		 * Eq.
		 *
		 * @return the terminal node
		 */
		public TerminalNode EQ() { return getToken(HdbsequenceParser.EQ, 0); }
		
		/**
		 * Int.
		 *
		 * @return the terminal node
		 */
		public TerminalNode INT() { return getToken(HdbsequenceParser.INT, 0); }
		
		/**
		 * Sc.
		 *
		 * @return the terminal node
		 */
		public TerminalNode SC() { return getToken(HdbsequenceParser.SC, 0); }
		
		/**
		 * Instantiates a new start with context.
		 *
		 * @param parent the parent
		 * @param invokingState the invoking state
		 */
		public Start_withContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		
		/**
		 * Gets the rule index.
		 *
		 * @return the rule index
		 */
		@Override public int getRuleIndex() { return RULE_start_with; }
		
		/**
		 * Enter rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof HdbsequenceListener ) ((HdbsequenceListener)listener).enterStart_with(this);
		}
		
		/**
		 * Exit rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof HdbsequenceListener ) ((HdbsequenceListener)listener).exitStart_with(this);
		}
		
		/**
		 * Accept.
		 *
		 * @param <T> the generic type
		 * @param visitor the visitor
		 * @return the t
		 */
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HdbsequenceVisitor ) return ((HdbsequenceVisitor<? extends T>)visitor).visitStart_with(this);
			else return visitor.visitChildren(this);
		}
	}

	/**
	 * Start with.
	 *
	 * @return the start with context
	 * @throws RecognitionException the recognition exception
	 */
	public final Start_withContext start_with() throws RecognitionException {
		Start_withContext _localctx = new Start_withContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_start_with);
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

	/**
	 * The Class MaxvalueContext.
	 */
	public static class MaxvalueContext extends ParserRuleContext {
		
		/**
		 * Eq.
		 *
		 * @return the terminal node
		 */
		public TerminalNode EQ() { return getToken(HdbsequenceParser.EQ, 0); }
		
		/**
		 * Int.
		 *
		 * @return the terminal node
		 */
		public TerminalNode INT() { return getToken(HdbsequenceParser.INT, 0); }
		
		/**
		 * Sc.
		 *
		 * @return the terminal node
		 */
		public TerminalNode SC() { return getToken(HdbsequenceParser.SC, 0); }
		
		/**
		 * Instantiates a new maxvalue context.
		 *
		 * @param parent the parent
		 * @param invokingState the invoking state
		 */
		public MaxvalueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		
		/**
		 * Gets the rule index.
		 *
		 * @return the rule index
		 */
		@Override public int getRuleIndex() { return RULE_maxvalue; }
		
		/**
		 * Enter rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof HdbsequenceListener ) ((HdbsequenceListener)listener).enterMaxvalue(this);
		}
		
		/**
		 * Exit rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof HdbsequenceListener ) ((HdbsequenceListener)listener).exitMaxvalue(this);
		}
		
		/**
		 * Accept.
		 *
		 * @param <T> the generic type
		 * @param visitor the visitor
		 * @return the t
		 */
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HdbsequenceVisitor ) return ((HdbsequenceVisitor<? extends T>)visitor).visitMaxvalue(this);
			else return visitor.visitChildren(this);
		}
	}

	/**
	 * Maxvalue.
	 *
	 * @return the maxvalue context
	 * @throws RecognitionException the recognition exception
	 */
	public final MaxvalueContext maxvalue() throws RecognitionException {
		MaxvalueContext _localctx = new MaxvalueContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_maxvalue);
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

	/**
	 * The Class NomaxvalueContext.
	 */
	public static class NomaxvalueContext extends ParserRuleContext {
		
		/**
		 * Eq.
		 *
		 * @return the terminal node
		 */
		public TerminalNode EQ() { return getToken(HdbsequenceParser.EQ, 0); }
		
		/**
		 * Boolean.
		 *
		 * @return the terminal node
		 */
		public TerminalNode BOOLEAN() { return getToken(HdbsequenceParser.BOOLEAN, 0); }
		
		/**
		 * Sc.
		 *
		 * @return the terminal node
		 */
		public TerminalNode SC() { return getToken(HdbsequenceParser.SC, 0); }
		
		/**
		 * Instantiates a new nomaxvalue context.
		 *
		 * @param parent the parent
		 * @param invokingState the invoking state
		 */
		public NomaxvalueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		
		/**
		 * Gets the rule index.
		 *
		 * @return the rule index
		 */
		@Override public int getRuleIndex() { return RULE_nomaxvalue; }
		
		/**
		 * Enter rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof HdbsequenceListener ) ((HdbsequenceListener)listener).enterNomaxvalue(this);
		}
		
		/**
		 * Exit rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof HdbsequenceListener ) ((HdbsequenceListener)listener).exitNomaxvalue(this);
		}
		
		/**
		 * Accept.
		 *
		 * @param <T> the generic type
		 * @param visitor the visitor
		 * @return the t
		 */
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HdbsequenceVisitor ) return ((HdbsequenceVisitor<? extends T>)visitor).visitNomaxvalue(this);
			else return visitor.visitChildren(this);
		}
	}

	/**
	 * Nomaxvalue.
	 *
	 * @return the nomaxvalue context
	 * @throws RecognitionException the recognition exception
	 */
	public final NomaxvalueContext nomaxvalue() throws RecognitionException {
		NomaxvalueContext _localctx = new NomaxvalueContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_nomaxvalue);
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

	/**
	 * The Class MinvalueContext.
	 */
	public static class MinvalueContext extends ParserRuleContext {
		
		/**
		 * Eq.
		 *
		 * @return the terminal node
		 */
		public TerminalNode EQ() { return getToken(HdbsequenceParser.EQ, 0); }
		
		/**
		 * Int.
		 *
		 * @return the terminal node
		 */
		public TerminalNode INT() { return getToken(HdbsequenceParser.INT, 0); }
		
		/**
		 * Sc.
		 *
		 * @return the terminal node
		 */
		public TerminalNode SC() { return getToken(HdbsequenceParser.SC, 0); }
		
		/**
		 * Instantiates a new minvalue context.
		 *
		 * @param parent the parent
		 * @param invokingState the invoking state
		 */
		public MinvalueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		
		/**
		 * Gets the rule index.
		 *
		 * @return the rule index
		 */
		@Override public int getRuleIndex() { return RULE_minvalue; }
		
		/**
		 * Enter rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof HdbsequenceListener ) ((HdbsequenceListener)listener).enterMinvalue(this);
		}
		
		/**
		 * Exit rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof HdbsequenceListener ) ((HdbsequenceListener)listener).exitMinvalue(this);
		}
		
		/**
		 * Accept.
		 *
		 * @param <T> the generic type
		 * @param visitor the visitor
		 * @return the t
		 */
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HdbsequenceVisitor ) return ((HdbsequenceVisitor<? extends T>)visitor).visitMinvalue(this);
			else return visitor.visitChildren(this);
		}
	}

	/**
	 * Minvalue.
	 *
	 * @return the minvalue context
	 * @throws RecognitionException the recognition exception
	 */
	public final MinvalueContext minvalue() throws RecognitionException {
		MinvalueContext _localctx = new MinvalueContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_minvalue);
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

	/**
	 * The Class NominvalueContext.
	 */
	public static class NominvalueContext extends ParserRuleContext {
		
		/**
		 * Eq.
		 *
		 * @return the terminal node
		 */
		public TerminalNode EQ() { return getToken(HdbsequenceParser.EQ, 0); }
		
		/**
		 * Boolean.
		 *
		 * @return the terminal node
		 */
		public TerminalNode BOOLEAN() { return getToken(HdbsequenceParser.BOOLEAN, 0); }
		
		/**
		 * Sc.
		 *
		 * @return the terminal node
		 */
		public TerminalNode SC() { return getToken(HdbsequenceParser.SC, 0); }
		
		/**
		 * Instantiates a new nominvalue context.
		 *
		 * @param parent the parent
		 * @param invokingState the invoking state
		 */
		public NominvalueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		
		/**
		 * Gets the rule index.
		 *
		 * @return the rule index
		 */
		@Override public int getRuleIndex() { return RULE_nominvalue; }
		
		/**
		 * Enter rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof HdbsequenceListener ) ((HdbsequenceListener)listener).enterNominvalue(this);
		}
		
		/**
		 * Exit rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof HdbsequenceListener ) ((HdbsequenceListener)listener).exitNominvalue(this);
		}
		
		/**
		 * Accept.
		 *
		 * @param <T> the generic type
		 * @param visitor the visitor
		 * @return the t
		 */
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HdbsequenceVisitor ) return ((HdbsequenceVisitor<? extends T>)visitor).visitNominvalue(this);
			else return visitor.visitChildren(this);
		}
	}

	/**
	 * Nominvalue.
	 *
	 * @return the nominvalue context
	 * @throws RecognitionException the recognition exception
	 */
	public final NominvalueContext nominvalue() throws RecognitionException {
		NominvalueContext _localctx = new NominvalueContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_nominvalue);
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

	/**
	 * The Class CyclesContext.
	 */
	public static class CyclesContext extends ParserRuleContext {
		
		/**
		 * Eq.
		 *
		 * @return the terminal node
		 */
		public TerminalNode EQ() { return getToken(HdbsequenceParser.EQ, 0); }
		
		/**
		 * Boolean.
		 *
		 * @return the terminal node
		 */
		public TerminalNode BOOLEAN() { return getToken(HdbsequenceParser.BOOLEAN, 0); }
		
		/**
		 * Sc.
		 *
		 * @return the terminal node
		 */
		public TerminalNode SC() { return getToken(HdbsequenceParser.SC, 0); }
		
		/**
		 * Instantiates a new cycles context.
		 *
		 * @param parent the parent
		 * @param invokingState the invoking state
		 */
		public CyclesContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		
		/**
		 * Gets the rule index.
		 *
		 * @return the rule index
		 */
		@Override public int getRuleIndex() { return RULE_cycles; }
		
		/**
		 * Enter rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof HdbsequenceListener ) ((HdbsequenceListener)listener).enterCycles(this);
		}
		
		/**
		 * Exit rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof HdbsequenceListener ) ((HdbsequenceListener)listener).exitCycles(this);
		}
		
		/**
		 * Accept.
		 *
		 * @param <T> the generic type
		 * @param visitor the visitor
		 * @return the t
		 */
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HdbsequenceVisitor ) return ((HdbsequenceVisitor<? extends T>)visitor).visitCycles(this);
			else return visitor.visitChildren(this);
		}
	}

	/**
	 * Cycles.
	 *
	 * @return the cycles context
	 * @throws RecognitionException the recognition exception
	 */
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

	/**
	 * The Class Reset_byContext.
	 */
	public static class Reset_byContext extends ParserRuleContext {
		
		/**
		 * Eq.
		 *
		 * @return the terminal node
		 */
		public TerminalNode EQ() { return getToken(HdbsequenceParser.EQ, 0); }
		
		/**
		 * String.
		 *
		 * @return the terminal node
		 */
		public TerminalNode STRING() { return getToken(HdbsequenceParser.STRING, 0); }
		
		/**
		 * Sc.
		 *
		 * @return the terminal node
		 */
		public TerminalNode SC() { return getToken(HdbsequenceParser.SC, 0); }
		
		/**
		 * Instantiates a new reset by context.
		 *
		 * @param parent the parent
		 * @param invokingState the invoking state
		 */
		public Reset_byContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		
		/**
		 * Gets the rule index.
		 *
		 * @return the rule index
		 */
		@Override public int getRuleIndex() { return RULE_reset_by; }
		
		/**
		 * Enter rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof HdbsequenceListener ) ((HdbsequenceListener)listener).enterReset_by(this);
		}
		
		/**
		 * Exit rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof HdbsequenceListener ) ((HdbsequenceListener)listener).exitReset_by(this);
		}
		
		/**
		 * Accept.
		 *
		 * @param <T> the generic type
		 * @param visitor the visitor
		 * @return the t
		 */
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HdbsequenceVisitor ) return ((HdbsequenceVisitor<? extends T>)visitor).visitReset_by(this);
			else return visitor.visitChildren(this);
		}
	}

	/**
	 * Reset by.
	 *
	 * @return the reset by context
	 * @throws RecognitionException the recognition exception
	 */
	public final Reset_byContext reset_by() throws RecognitionException {
		Reset_byContext _localctx = new Reset_byContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_reset_by);
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

	/**
	 * The Class PubliccContext.
	 */
	public static class PubliccContext extends ParserRuleContext {
		
		/**
		 * Eq.
		 *
		 * @return the terminal node
		 */
		public TerminalNode EQ() { return getToken(HdbsequenceParser.EQ, 0); }
		
		/**
		 * Boolean.
		 *
		 * @return the terminal node
		 */
		public TerminalNode BOOLEAN() { return getToken(HdbsequenceParser.BOOLEAN, 0); }
		
		/**
		 * Sc.
		 *
		 * @return the terminal node
		 */
		public TerminalNode SC() { return getToken(HdbsequenceParser.SC, 0); }
		
		/**
		 * Instantiates a new publicc context.
		 *
		 * @param parent the parent
		 * @param invokingState the invoking state
		 */
		public PubliccContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		
		/**
		 * Gets the rule index.
		 *
		 * @return the rule index
		 */
		@Override public int getRuleIndex() { return RULE_publicc; }
		
		/**
		 * Enter rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof HdbsequenceListener ) ((HdbsequenceListener)listener).enterPublicc(this);
		}
		
		/**
		 * Exit rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof HdbsequenceListener ) ((HdbsequenceListener)listener).exitPublicc(this);
		}
		
		/**
		 * Accept.
		 *
		 * @param <T> the generic type
		 * @param visitor the visitor
		 * @return the t
		 */
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HdbsequenceVisitor ) return ((HdbsequenceVisitor<? extends T>)visitor).visitPublicc(this);
			else return visitor.visitChildren(this);
		}
	}

	/**
	 * Publicc.
	 *
	 * @return the publicc context
	 * @throws RecognitionException the recognition exception
	 */
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

	/**
	 * The Class DependsOnTableContext.
	 */
	public static class DependsOnTableContext extends ParserRuleContext {
		
		/**
		 * Eq.
		 *
		 * @return the terminal node
		 */
		public TerminalNode EQ() { return getToken(HdbsequenceParser.EQ, 0); }
		
		/**
		 * String.
		 *
		 * @return the terminal node
		 */
		public TerminalNode STRING() { return getToken(HdbsequenceParser.STRING, 0); }
		
		/**
		 * Sc.
		 *
		 * @return the terminal node
		 */
		public TerminalNode SC() { return getToken(HdbsequenceParser.SC, 0); }
		
		/**
		 * Instantiates a new depends on table context.
		 *
		 * @param parent the parent
		 * @param invokingState the invoking state
		 */
		public DependsOnTableContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		
		/**
		 * Gets the rule index.
		 *
		 * @return the rule index
		 */
		@Override public int getRuleIndex() { return RULE_dependsOnTable; }
		
		/**
		 * Enter rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof HdbsequenceListener ) ((HdbsequenceListener)listener).enterDependsOnTable(this);
		}
		
		/**
		 * Exit rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof HdbsequenceListener ) ((HdbsequenceListener)listener).exitDependsOnTable(this);
		}
		
		/**
		 * Accept.
		 *
		 * @param <T> the generic type
		 * @param visitor the visitor
		 * @return the t
		 */
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HdbsequenceVisitor ) return ((HdbsequenceVisitor<? extends T>)visitor).visitDependsOnTable(this);
			else return visitor.visitChildren(this);
		}
	}

	/**
	 * Depends on table.
	 *
	 * @return the depends on table context
	 * @throws RecognitionException the recognition exception
	 */
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

	/**
	 * The Class DependsOnViewContext.
	 */
	public static class DependsOnViewContext extends ParserRuleContext {
		
		/**
		 * Eq.
		 *
		 * @return the terminal node
		 */
		public TerminalNode EQ() { return getToken(HdbsequenceParser.EQ, 0); }
		
		/**
		 * String.
		 *
		 * @return the terminal node
		 */
		public TerminalNode STRING() { return getToken(HdbsequenceParser.STRING, 0); }
		
		/**
		 * Sc.
		 *
		 * @return the terminal node
		 */
		public TerminalNode SC() { return getToken(HdbsequenceParser.SC, 0); }
		
		/**
		 * Instantiates a new depends on view context.
		 *
		 * @param parent the parent
		 * @param invokingState the invoking state
		 */
		public DependsOnViewContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		
		/**
		 * Gets the rule index.
		 *
		 * @return the rule index
		 */
		@Override public int getRuleIndex() { return RULE_dependsOnView; }
		
		/**
		 * Enter rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof HdbsequenceListener ) ((HdbsequenceListener)listener).enterDependsOnView(this);
		}
		
		/**
		 * Exit rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof HdbsequenceListener ) ((HdbsequenceListener)listener).exitDependsOnView(this);
		}
		
		/**
		 * Accept.
		 *
		 * @param <T> the generic type
		 * @param visitor the visitor
		 * @return the t
		 */
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HdbsequenceVisitor ) return ((HdbsequenceVisitor<? extends T>)visitor).visitDependsOnView(this);
			else return visitor.visitChildren(this);
		}
	}

	/**
	 * Depends on view.
	 *
	 * @return the depends on view context
	 * @throws RecognitionException the recognition exception
	 */
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

	/** The Constant _serializedATN. */
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
	
	/** The Constant _ATN. */
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}