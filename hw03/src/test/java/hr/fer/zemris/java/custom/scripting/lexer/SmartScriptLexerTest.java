package hr.fer.zemris.java.custom.scripting.lexer;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class SmartScriptLexerTest {
	
	@Test
	public void TestEmpty() {
		SmartScriptLexer lexer = new SmartScriptLexer("");
		SmartScriptToken correctData[] = {
				new SmartScriptToken(SmartScriptTokenType.EOF, null)
		};
		checkTokenStream(lexer, correctData);
	}
	
	@Test
	public void TestText() {
		SmartScriptLexer lexer = new SmartScriptLexer("This is the first test");
		SmartScriptToken correctData[] = {
				new SmartScriptToken(SmartScriptTokenType.TEXT, "This is the first test")
		};
		checkTokenStream(lexer, correctData);
	}
	
	@Test
	public void TestTextWithWightspace() {
		SmartScriptLexer lexer = new SmartScriptLexer("This is the \r\n\t first test\n");
		SmartScriptToken correctData[] = {
				new SmartScriptToken(SmartScriptTokenType.TEXT, "This is the \r\n\t first test\n")
		};
		checkTokenStream(lexer, correctData);
	}
	@Test
	public void TestTextWithBackslash() {
		SmartScriptLexer lexer = new SmartScriptLexer("This is the \\{ first test\n");
		SmartScriptToken correctData[] = {
				new SmartScriptToken(SmartScriptTokenType.TEXT, "This is the { first test\n")
		};
		checkTokenStream(lexer, correctData);
	}
	
	@Test
	public void TestTextWithAllSpecialSymbols() {
		SmartScriptLexer lexer = new SmartScriptLexer("This\t is\r the \\{ first\\\\ test\n");
		SmartScriptToken correctData[] = {
				new SmartScriptToken(SmartScriptTokenType.TEXT, "This\t is\r the { first\\ test\n")
		};
		checkTokenStream(lexer, correctData);
	}
	
	
	@Test (expected=SmartScriptLexerException.class)
	public void TestTextWithBackslashAloneAtEnd() {
		SmartScriptLexer lexer = new SmartScriptLexer("This is the test for \\");
		SmartScriptToken correctData[] = {
				new SmartScriptToken(SmartScriptTokenType.TEXT, "This is the test for \\")
		};
		checkTokenStream(lexer, correctData);
	}
	
	@Test
	public void TestEmptyTag() {
		SmartScriptLexer lexer = new SmartScriptLexer("{$$}");
		SmartScriptToken correctData[] = {
				
				new SmartScriptToken(SmartScriptTokenType.TAGSTART, "{$"),
				new SmartScriptToken(SmartScriptTokenType.TAGEND, "$}")
			};

			checkTokenStream(lexer, correctData);
	}
	
	
	@Test
	public void TestEmptyTagAndText() {
		SmartScriptLexer lexer = new SmartScriptLexer("{$$}\n\t tekst");
		
		SmartScriptToken correctData[] = {
				
				new SmartScriptToken(SmartScriptTokenType.TAGSTART, "{$"),
				new SmartScriptToken(SmartScriptTokenType.TAGEND, "$}"),
				new SmartScriptToken(SmartScriptTokenType.TEXT, "\n\t tekst")
			};

			checkTokenStream(lexer, correctData);
	}
	@Test
	public void TestTag() {
		SmartScriptLexer lexer = new SmartScriptLexer("{$ FOR -1 1 1 $}a");
		
		SmartScriptToken correctData[] = {
				
				new SmartScriptToken(SmartScriptTokenType.TAGSTART, "{$"),
				new SmartScriptToken(SmartScriptTokenType.SPACE, " "),
				new SmartScriptToken(SmartScriptTokenType.FOR, "FOR"),
				new SmartScriptToken(SmartScriptTokenType.SPACE, " "),
				new SmartScriptToken(SmartScriptTokenType.INTEGER, -1),
				new SmartScriptToken(SmartScriptTokenType.SPACE, " "),
				new SmartScriptToken(SmartScriptTokenType.INTEGER, 1),
				new SmartScriptToken(SmartScriptTokenType.SPACE, " "),
				new SmartScriptToken(SmartScriptTokenType.INTEGER, 1),
				new SmartScriptToken(SmartScriptTokenType.SPACE, " "),
				new SmartScriptToken(SmartScriptTokenType.TAGEND, "$}"),
				new SmartScriptToken(SmartScriptTokenType.TEXT, "a"),
				new SmartScriptToken(SmartScriptTokenType.EOF, null)
			};

			checkTokenStream(lexer, correctData);
	}
	
	
	@Test
	public void TestTagWithDoubleAndVariable() {
		SmartScriptLexer lexer = new SmartScriptLexer("{$ FOR -1.4 aaa 1 $}a");
		
		SmartScriptToken correctData[] = {
				
				new SmartScriptToken(SmartScriptTokenType.TAGSTART, "{$"),
				new SmartScriptToken(SmartScriptTokenType.SPACE, " "),
				new SmartScriptToken(SmartScriptTokenType.FOR, "FOR"),
				new SmartScriptToken(SmartScriptTokenType.SPACE, " "),
				new SmartScriptToken(SmartScriptTokenType.DOUBLE, -1.4),
				new SmartScriptToken(SmartScriptTokenType.SPACE, " "),
				new SmartScriptToken(SmartScriptTokenType.VARIABLE, "aaa"),
				new SmartScriptToken(SmartScriptTokenType.SPACE, " "),
				new SmartScriptToken(SmartScriptTokenType.INTEGER, 1),
				new SmartScriptToken(SmartScriptTokenType.SPACE, " "),
				new SmartScriptToken(SmartScriptTokenType.TAGEND, "$}"),
				new SmartScriptToken(SmartScriptTokenType.TEXT, "a"),
				new SmartScriptToken(SmartScriptTokenType.EOF, null)
			};

			checkTokenStream(lexer, correctData);
	}
	
	@Test
	public void TestTagQuouted() {
		SmartScriptLexer lexer = new SmartScriptLexer("{$\"-4\" $}a");
		
		SmartScriptToken correctData[] = {
				
				new SmartScriptToken(SmartScriptTokenType.TAGSTART, "{$"),
				new SmartScriptToken(SmartScriptTokenType.STRING, "-4" ),
				new SmartScriptToken(SmartScriptTokenType.SPACE, " "),
				new SmartScriptToken(SmartScriptTokenType.TAGEND, "$}"),
				new SmartScriptToken(SmartScriptTokenType.TEXT, "a"),
				new SmartScriptToken(SmartScriptTokenType.EOF, null)
			};

			checkTokenStream(lexer, correctData);
	}
	@Test
	public void TestAllTypes() {
		SmartScriptLexer lexer = new SmartScriptLexer("{$=FOR sco_re \"-1\"10 @sin * $}");
	
	SmartScriptToken correctData[] = {
			
			new SmartScriptToken(SmartScriptTokenType.TAGSTART, "{$"),
			new SmartScriptToken(SmartScriptTokenType.EQUALS, "="),
			new SmartScriptToken(SmartScriptTokenType.FOR, "FOR"),
			new SmartScriptToken(SmartScriptTokenType.SPACE, " "),
			new SmartScriptToken(SmartScriptTokenType.VARIABLE, "sco_re"),
			new SmartScriptToken(SmartScriptTokenType.SPACE, " "),
			new SmartScriptToken(SmartScriptTokenType.STRING, "-1"),
			new SmartScriptToken(SmartScriptTokenType.INTEGER, 10),
			new SmartScriptToken(SmartScriptTokenType.SPACE, " "),
			new SmartScriptToken(SmartScriptTokenType.FUNCION, "sin"),
			new SmartScriptToken(SmartScriptTokenType.SPACE, " "),
			new SmartScriptToken(SmartScriptTokenType.OPERATOR, "*"),
			new SmartScriptToken(SmartScriptTokenType.SPACE, " "),
			new SmartScriptToken(SmartScriptTokenType.TAGEND, "$}")
		};

		checkTokenStream(lexer, correctData);
	}
	
	@Test
	public void TestExampleText() {
		SmartScriptLexer lexer = new SmartScriptLexer("This is sample text.\r\n" + 
				"{$ FOR i 1 10 1 $}\r\n" + 
				" This is {$= i $}-th time this message is generated.\r\n" + 
				"{$END$}\r\n" + 
				"{$FOR i 0 10 2 $}\r\n");
	
	SmartScriptToken correctData[] = {
			
			new SmartScriptToken(SmartScriptTokenType.TEXT, "This is sample text.\r\n"),
			new SmartScriptToken(SmartScriptTokenType.TAGSTART, "{$"),
			new SmartScriptToken(SmartScriptTokenType.SPACE, " "),
			new SmartScriptToken(SmartScriptTokenType.FOR, "FOR"),
			new SmartScriptToken(SmartScriptTokenType.SPACE, " "),
			new SmartScriptToken(SmartScriptTokenType.VARIABLE, "i"),
			new SmartScriptToken(SmartScriptTokenType.SPACE, " "),
			new SmartScriptToken(SmartScriptTokenType.INTEGER, 1),
			new SmartScriptToken(SmartScriptTokenType.SPACE, " "),
			new SmartScriptToken(SmartScriptTokenType.INTEGER, 10),
			new SmartScriptToken(SmartScriptTokenType.SPACE, " "),
			new SmartScriptToken(SmartScriptTokenType.INTEGER, 1),
			new SmartScriptToken(SmartScriptTokenType.SPACE, " "),
			new SmartScriptToken(SmartScriptTokenType.TAGEND, "$}"),
			new SmartScriptToken(SmartScriptTokenType.TEXT, "\r\n This is "),
			new SmartScriptToken(SmartScriptTokenType.TAGSTART, "{$"),
			new SmartScriptToken(SmartScriptTokenType.EQUALS, "="),
			new SmartScriptToken(SmartScriptTokenType.SPACE, " "),
			new SmartScriptToken(SmartScriptTokenType.VARIABLE, "i"),
			new SmartScriptToken(SmartScriptTokenType.SPACE, " "),
			new SmartScriptToken(SmartScriptTokenType.TAGEND, "$}"),
			new SmartScriptToken(SmartScriptTokenType.TEXT, "-th time this message is generated.\r\n"),
			new SmartScriptToken(SmartScriptTokenType.TAGSTART, "{$"),
			new SmartScriptToken(SmartScriptTokenType.END, "END"),
			new SmartScriptToken(SmartScriptTokenType.TAGEND, "$}"),
			new SmartScriptToken(SmartScriptTokenType.TEXT, "\r\n"),
			new SmartScriptToken(SmartScriptTokenType.TAGSTART, "{$"),
			new SmartScriptToken(SmartScriptTokenType.FOR, "FOR"),
			new SmartScriptToken(SmartScriptTokenType.SPACE, " "),
			new SmartScriptToken(SmartScriptTokenType.VARIABLE, "i"),
			new SmartScriptToken(SmartScriptTokenType.SPACE, " "),
			new SmartScriptToken(SmartScriptTokenType.INTEGER, 0),
			new SmartScriptToken(SmartScriptTokenType.SPACE, " "),
			new SmartScriptToken(SmartScriptTokenType.INTEGER, 10),
			new SmartScriptToken(SmartScriptTokenType.SPACE, " "),
			new SmartScriptToken(SmartScriptTokenType.INTEGER, 2),
			new SmartScriptToken(SmartScriptTokenType.SPACE, " "),
			new SmartScriptToken(SmartScriptTokenType.TAGEND, "$}"),
			new SmartScriptToken(SmartScriptTokenType.TEXT, "\r\n"),
			
		};

		checkTokenStream(lexer, correctData);
	}
	
	private void checkTokenStream(SmartScriptLexer lexer, SmartScriptToken[] correctData) {
		int counter = 0;
		for(SmartScriptToken expected : correctData) {
			SmartScriptToken actual = lexer.nextToken();
			String msg = "Checking token "+counter + ":";
			assertEquals(msg, expected.getType(), actual.getType());
			assertEquals(msg, expected.getValue(), actual.getValue());
			counter++;
		}
	}
}

