package designpattern.practice;

public class Adapter {
	
	private static class XmlDataSource {
		private String getXMLContent() {
            return "<user><name>John</name><age>25</age></user>";			
		}
	}
	
	private static interface JsonDataReader {
        String readAsJson();		
	}
	
	private static class XmlToJsonAdapter implements JsonDataReader {
		private XmlDataSource xmlDataSource;
		
		XmlToJsonAdapter(XmlDataSource _xmlDataSource) {
			xmlDataSource = _xmlDataSource;
		}

		@Override
		public String readAsJson() {
			// TODO Auto-generated method stub
			String xmlContent = xmlDataSource.getXMLContent();
			return xmlToJson(xmlContent);
		}

		private String xmlToJson(String xml) {
            StringBuilder json = new StringBuilder("{");
            String remaining = xml.trim();
            boolean firstField = true;
            while (remaining.contains("<")) {
                int tagStart = remaining.indexOf('<');
                int tagEnd = remaining.indexOf('>', tagStart);
                if (tagEnd == -1) {
                    break;
                }
                // Closing tag — skip it
                if (remaining.charAt(tagStart + 1) == '/') {
                    remaining = remaining.substring(tagEnd + 1).trim();
                    continue;
                }
                // Extract tag name
                int tagNameStart = tagStart + 1;
                int tagNameEnd = tagNameStart;
                while (tagNameEnd < tagEnd
                        && !Character.isWhitespace(remaining.charAt(tagNameEnd))
                        && remaining.charAt(tagNameEnd) != '/') {
                    tagNameEnd++;
                }
                String tagName = remaining.substring(tagNameStart, tagNameEnd);

                // Find closing tag and extract content between the two tags
                int contentStart = tagEnd + 1;
                String closingTag = "</" + tagName + ">";
                int closingTagStart = remaining.indexOf(closingTag, contentStart);
                if (closingTagStart == -1) {
                    break;
                }
                String content = remaining.substring(contentStart, closingTagStart).trim();

                if (!firstField) {
                    json.append(",");
                }
                json.append("\"").append(tagName).append("\":");
                if (content.contains("<")) {
                    json.append(xmlToJson(content));
                } else {
                    json.append("\"").append(content.replace("\"", "\\\"")).append("\"");
                }
                firstField = false;

                remaining = remaining.substring(closingTagStart + closingTag.length()).trim();
            }
            json.append("}");
            return json.toString();
        }
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		XmlDataSource source = new XmlDataSource();
		JsonDataReader reader = new XmlToJsonAdapter(source);
		String jsonVal = reader.readAsJson();
	}

}
