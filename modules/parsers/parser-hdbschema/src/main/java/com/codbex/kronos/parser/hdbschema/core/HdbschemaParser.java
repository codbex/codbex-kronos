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
// Generated from com/codbex/kronos/parser/hdbschema/core/Hdbschema.g4 by ANTLR 4.10.1
package com.codbex.kronos.parser.hdbschema.core;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

/**
 * The Class HdbschemaParser.
 */
@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class HdbschemaParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.10.1", RuntimeMetaData.VERSION); }

	/** The Constant _decisionToDFA. */
	protected static final DFA[] _decisionToDFA;
	
	/** The Constant _sharedContextCache. */
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	
	/** The Constant COMMENT. */
	public static final int
		T__0=1, STRING=2, EQ=3, SEMICOLON=4, COMMA=5, WS=6, ESC=7, LINE_COMMENT=8, 
		COMMENT=9;
	
	/** The Constant RULE_schemaNameProp. */
	public static final int
		RULE_hdbschemaDefinition = 0, RULE_schemaNameProp = 1;
	
	/**
	 * Make rule names.
	 *
	 * @return the string[]
	 */
	private static String[] makeRuleNames() {
		return new String[] {
			"hdbschemaDefinition", "schemaNameProp"
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
			null, "'schema_name'", null, "'='", "';'", "','"
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
			null, null, "STRING", "EQ", "SEMICOLON", "COMMA", "WS", "ESC", "LINE_COMMENT", 
			"COMMENT"
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
	public String getGrammarFileName() { return "Hdbschema.g4"; }

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
	 * Instantiates a new hdbschema parser.
	 *
	 * @param input the input
	 */
	public HdbschemaParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	/**
	 * The Class HdbschemaDefinitionContext.
	 */
	public static class HdbschemaDefinitionContext extends ParserRuleContext {
		
		/**
		 * Schema name prop.
		 *
		 * @return the schema name prop context
		 */
		public SchemaNamePropContext schemaNameProp() {
			return getRuleContext(SchemaNamePropContext.class,0);
		}
		
		/**
		 * Instantiates a new hdbschema definition context.
		 *
		 * @param parent the parent
		 * @param invokingState the invoking state
		 */
		public HdbschemaDefinitionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		
		/**
		 * Gets the rule index.
		 *
		 * @return the rule index
		 */
		@Override public int getRuleIndex() { return RULE_hdbschemaDefinition; }
		
		/**
		 * Enter rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof HdbschemaListener ) ((HdbschemaListener)listener).enterHdbschemaDefinition(this);
		}
		
		/**
		 * Exit rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof HdbschemaListener ) ((HdbschemaListener)listener).exitHdbschemaDefinition(this);
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
			if ( visitor instanceof HdbschemaVisitor ) return ((HdbschemaVisitor<? extends T>)visitor).visitHdbschemaDefinition(this);
			else return visitor.visitChildren(this);
		}
	}

	/**
	 * Hdbschema definition.
	 *
	 * @return the hdbschema definition context
	 * @throws RecognitionException the recognition exception
	 */
	public final HdbschemaDefinitionContext hdbschemaDefinition() throws RecognitionException {
		HdbschemaDefinitionContext _localctx = new HdbschemaDefinitionContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_hdbschemaDefinition);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(4);
			schemaNameProp();
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
	 * The Class SchemaNamePropContext.
	 */
	public static class SchemaNamePropContext extends ParserRuleContext {
		
		/**
		 * Eq.
		 *
		 * @return the terminal node
		 */
		public TerminalNode EQ() { return getToken(HdbschemaParser.EQ, 0); }
		
		/**
		 * String.
		 *
		 * @return the terminal node
		 */
		public TerminalNode STRING() { return getToken(HdbschemaParser.STRING, 0); }
		
		/**
		 * Semicolon.
		 *
		 * @return the terminal node
		 */
		public TerminalNode SEMICOLON() { return getToken(HdbschemaParser.SEMICOLON, 0); }
		
		/**
		 * Instantiates a new schema name prop context.
		 *
		 * @param parent the parent
		 * @param invokingState the invoking state
		 */
		public SchemaNamePropContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		
		/**
		 * Gets the rule index.
		 *
		 * @return the rule index
		 */
		@Override public int getRuleIndex() { return RULE_schemaNameProp; }
		
		/**
		 * Enter rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof HdbschemaListener ) ((HdbschemaListener)listener).enterSchemaNameProp(this);
		}
		
		/**
		 * Exit rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof HdbschemaListener ) ((HdbschemaListener)listener).exitSchemaNameProp(this);
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
			if ( visitor instanceof HdbschemaVisitor ) return ((HdbschemaVisitor<? extends T>)visitor).visitSchemaNameProp(this);
			else return visitor.visitChildren(this);
		}
	}

	/**
	 * Schema name prop.
	 *
	 * @return the schema name prop context
	 * @throws RecognitionException the recognition exception
	 */
	public final SchemaNamePropContext schemaNameProp() throws RecognitionException {
		SchemaNamePropContext _localctx = new SchemaNamePropContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_schemaNameProp);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(6);
			match(T__0);
			setState(7);
			match(EQ);
			setState(8);
			match(STRING);
			setState(9);
			match(SEMICOLON);
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
		"\u0004\u0001\t\f\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0001"+
		"\u0000\u0001\u0000\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0000\u0000\u0002\u0000\u0002\u0000\u0000\t\u0000\u0004"+
		"\u0001\u0000\u0000\u0000\u0002\u0006\u0001\u0000\u0000\u0000\u0004\u0005"+
		"\u0003\u0002\u0001\u0000\u0005\u0001\u0001\u0000\u0000\u0000\u0006\u0007"+
		"\u0005\u0001\u0000\u0000\u0007\b\u0005\u0003\u0000\u0000\b\t\u0005\u0002"+
		"\u0000\u0000\t\n\u0005\u0004\u0000\u0000\n\u0003\u0001\u0000\u0000\u0000"+
		"\u0000";
	
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