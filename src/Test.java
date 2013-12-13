
public class Test {

	public static void main(String[] args) {
		String userCommand;
		DocumentCollection dc = new DocumentCollection();
		Document command;
		
		while(true)	{
			System.out.println("Folgende Befehle zählen als gültig:");
			System.out.println("- exit: Beendet das Programm");
			System.out.println("- add: Fügt ein neues Dokument hinzu");
			System.out.println("- list: Gibt alle per 'add' hinzugefügten Dokumente aus");
			System.out.println("- count: Gibt aus wie oft das Wort pro Dokument auftritt");
			System.out.println("- query: Suchanfrage in den Dokumenten");
			
			userCommand = Terminal.askString("> ");
			command = new Document(null, null, null, null, null, userCommand);
			
			if (command.getWordCounts().getWord(0).equals("exit"))	{
				System.out.println("Das Programm wird beendet.");
				break;
				
			}	else if (command.getWordCounts().getWord(0).equals("add"))	{
				String[] userCommandArray = new String[2];
				int start = 0, mid = 0;
				for (int i = 0; i < userCommand.length(); i++)	{
					if ((userCommand.charAt(i) == ' ') && (start == 0))	{
						start = i + 1;
					}	else if (userCommand.charAt(i) == ':')	{
						mid = i;
						userCommandArray[0] = userCommand.substring(start, mid);
					}
				}
				if (mid == 0)	{
					System.out.println("Der Befehl war nicht in einem Gültigen Syntax angegeben.");
				}	else	{
					userCommandArray[1] = userCommand.substring(mid + 1);
					Document doc = new Document(userCommandArray[0], null, null, null, null, userCommandArray[1]);
					dc.addLast(doc);
				}
				
			}	else if (command.getWordCounts().getWord(0).equals("list"))	{
				System.out.println("Folgende Dokumente sind aktuell gelistet:");
				if (dc.size() < 1)	{
					System.out.println("Die Liste ist leer.");
				}	else	{
					for (int i = 0; i < dc.size(); i++)	{
						System.out.println((i + 1) + ". " + dc.get(i).getTitle());
					}
				}
				
			}	else if (command.getWordCounts().getWord(0).equals("count"))	{
				if (dc.size() < 1)	{
					System.out.println("Die Liste ist leer.");
				}	else	{
					String word = command.getWordCounts().getWord(1);
					int count = 0;
					for (int i = 0; i < dc.size(); i++)	{
						count = dc.get(i).getWordCounts().getCount(dc.get(i).getWordCounts().getIndex(word));
						System.out.print("\n- " + dc.get(i).getTitle() + ": ");
						if (count > -1)	{
							System.out.print(count + "x\n");
						}	else	{
							System.out.print("0x\n");
						}
					}
				}
				
			}	else if (command.getWordCounts().getWord(0).equals("query"))	{
				if (dc.size() < 1)	{
					System.out.println("Die Liste ist leer.");
				}	else	{
					String temp = "";
					
					for (int i = 1; i < dc.size(); i++)	{
						temp += command.getWordCounts().getWord(i) + " ";
					}
					
					dc.match(temp);
				}
				
			}	else	{
				System.out.println("Kein gültiger Befehl.");
				
			}
			
			System.out.println();
			System.out.println();
		}
		
	}

}
