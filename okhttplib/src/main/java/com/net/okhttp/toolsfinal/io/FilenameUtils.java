package com.net.okhttp.toolsfinal.io;

/**
 * Created by 20164237 on 2016/4/19.
 */
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class FilenameUtils {
    public static final char EXTENSION_SEPARATOR = '.';
    public static final String EXTENSION_SEPARATOR_STR = Character.toString('.');
    private static final char UNIX_SEPARATOR = '/';
    private static final char WINDOWS_SEPARATOR = '\\';
    private static final char SYSTEM_SEPARATOR;
    private static final char OTHER_SEPARATOR;

    public FilenameUtils() {
    }

    static boolean isSystemWindows() {
        return SYSTEM_SEPARATOR == 92;
    }

    private static boolean isSeparator(char ch) {
        return ch == 47 || ch == 92;
    }

    public static String normalize(String filename) {
        return doNormalize(filename, SYSTEM_SEPARATOR, true);
    }

    public static String normalize(String filename, boolean unixSeparator) {
        int separator = unixSeparator?47:92;
        return doNormalize(filename, (char)separator, true);
    }

    public static String normalizeNoEndSeparator(String filename) {
        return doNormalize(filename, SYSTEM_SEPARATOR, false);
    }

    public static String normalizeNoEndSeparator(String filename, boolean unixSeparator) {
        int separator = unixSeparator?47:92;
        return doNormalize(filename, (char)separator, false);
    }

    private static String doNormalize(String filename, char separator, boolean keepSeparator) {
        if(filename == null) {
            return null;
        } else {
            int size = filename.length();
            if(size == 0) {
                return filename;
            } else {
                int prefix = getPrefixLength(filename);
                if(prefix < 0) {
                    return null;
                } else {
                    char[] array = new char[size + 2];
                    filename.getChars(0, filename.length(), array, 0);
                    char otherSeparator = separator == SYSTEM_SEPARATOR?OTHER_SEPARATOR:SYSTEM_SEPARATOR;

                    for(int lastIsDirectory = 0; lastIsDirectory < array.length; ++lastIsDirectory) {
                        if(array[lastIsDirectory] == otherSeparator) {
                            array[lastIsDirectory] = separator;
                        }
                    }

                    boolean var10 = true;
                    if(array[size - 1] != separator) {
                        array[size++] = separator;
                        var10 = false;
                    }

                    int i;
                    for(i = prefix + 1; i < size; ++i) {
                        if(array[i] == separator && array[i - 1] == separator) {
                            System.arraycopy(array, i, array, i - 1, size - i);
                            --size;
                            --i;
                        }
                    }

                    for(i = prefix + 1; i < size; ++i) {
                        if(array[i] == separator && array[i - 1] == 46 && (i == prefix + 1 || array[i - 2] == separator)) {
                            if(i == size - 1) {
                                var10 = true;
                            }

                            System.arraycopy(array, i + 1, array, i - 1, size - i);
                            size -= 2;
                            --i;
                        }
                    }

                    label109:
                    for(i = prefix + 2; i < size; ++i) {
                        if(array[i] == separator && array[i - 1] == 46 && array[i - 2] == 46 && (i == prefix + 2 || array[i - 3] == separator)) {
                            if(i == prefix + 2) {
                                return null;
                            }

                            if(i == size - 1) {
                                var10 = true;
                            }

                            for(int j = i - 4; j >= prefix; --j) {
                                if(array[j] == separator) {
                                    System.arraycopy(array, i + 1, array, j + 1, size - i);
                                    size -= i - j;
                                    i = j + 1;
                                    continue label109;
                                }
                            }

                            System.arraycopy(array, i + 1, array, prefix, size - i);
                            size -= i + 1 - prefix;
                            i = prefix + 1;
                        }
                    }

                    if(size <= 0) {
                        return "";
                    } else if(size <= prefix) {
                        return new String(array, 0, size);
                    } else if(var10 && keepSeparator) {
                        return new String(array, 0, size);
                    } else {
                        return new String(array, 0, size - 1);
                    }
                }
            }
        }
    }

    public static String concat(String basePath, String fullFilenameToAdd) {
        int prefix = getPrefixLength(fullFilenameToAdd);
        if(prefix < 0) {
            return null;
        } else if(prefix > 0) {
            return normalize(fullFilenameToAdd);
        } else if(basePath == null) {
            return null;
        } else {
            int len = basePath.length();
            if(len == 0) {
                return normalize(fullFilenameToAdd);
            } else {
                char ch = basePath.charAt(len - 1);
                return isSeparator(ch)?normalize(basePath + fullFilenameToAdd):normalize(basePath + '/' + fullFilenameToAdd);
            }
        }
    }

    public static String separatorsToUnix(String path) {
        return path != null && path.indexOf(92) != -1?path.replace('\\', '/'):path;
    }

    public static String separatorsToWindows(String path) {
        return path != null && path.indexOf(47) != -1?path.replace('/', '\\'):path;
    }

    public static String separatorsToSystem(String path) {
        return path == null?null:(isSystemWindows()?separatorsToWindows(path):separatorsToUnix(path));
    }

    public static int getPrefixLength(String filename) {
        if(filename == null) {
            return -1;
        } else {
            int len = filename.length();
            if(len == 0) {
                return 0;
            } else {
                char ch0 = filename.charAt(0);
                if(ch0 == 58) {
                    return -1;
                } else if(len == 1) {
                    return ch0 == 126?2:(isSeparator(ch0)?1:0);
                } else {
                    int posUnix;
                    if(ch0 == 126) {
                        int ch11 = filename.indexOf(47, 1);
                        posUnix = filename.indexOf(92, 1);
                        if(ch11 == -1 && posUnix == -1) {
                            return len + 1;
                        } else {
                            ch11 = ch11 == -1?posUnix:ch11;
                            posUnix = posUnix == -1?ch11:posUnix;
                            return Math.min(ch11, posUnix) + 1;
                        }
                    } else {
                        char ch1 = filename.charAt(1);
                        if(ch1 == 58) {
                            ch0 = Character.toUpperCase(ch0);
                            return ch0 >= 65 && ch0 <= 90?(len != 2 && isSeparator(filename.charAt(2))?3:2):-1;
                        } else if(isSeparator(ch0) && isSeparator(ch1)) {
                            posUnix = filename.indexOf(47, 2);
                            int posWin = filename.indexOf(92, 2);
                            if((posUnix != -1 || posWin != -1) && posUnix != 2 && posWin != 2) {
                                posUnix = posUnix == -1?posWin:posUnix;
                                posWin = posWin == -1?posUnix:posWin;
                                return Math.min(posUnix, posWin) + 1;
                            } else {
                                return -1;
                            }
                        } else {
                            return isSeparator(ch0)?1:0;
                        }
                    }
                }
            }
        }
    }

    public static int indexOfLastSeparator(String filename) {
        if(filename == null) {
            return -1;
        } else {
            int lastUnixPos = filename.lastIndexOf(47);
            int lastWindowsPos = filename.lastIndexOf(92);
            return Math.max(lastUnixPos, lastWindowsPos);
        }
    }

    public static int indexOfExtension(String filename) {
        if(filename == null) {
            return -1;
        } else {
            int extensionPos = filename.lastIndexOf(46);
            int lastSeparator = indexOfLastSeparator(filename);
            return lastSeparator > extensionPos?-1:extensionPos;
        }
    }

    public static String getPrefix(String filename) {
        if(filename == null) {
            return null;
        } else {
            int len = getPrefixLength(filename);
            return len < 0?null:(len > filename.length()?filename + '/':filename.substring(0, len));
        }
    }

    public static String getPath(String filename) {
        return doGetPath(filename, 1);
    }

    public static String getPathNoEndSeparator(String filename) {
        return doGetPath(filename, 0);
    }

    private static String doGetPath(String filename, int separatorAdd) {
        if(filename == null) {
            return null;
        } else {
            int prefix = getPrefixLength(filename);
            if(prefix < 0) {
                return null;
            } else {
                int index = indexOfLastSeparator(filename);
                int endIndex = index + separatorAdd;
                return prefix < filename.length() && index >= 0 && prefix < endIndex?filename.substring(prefix, endIndex):"";
            }
        }
    }

    public static String getFullPath(String filename) {
        return doGetFullPath(filename, true);
    }

    public static String getFullPathNoEndSeparator(String filename) {
        return doGetFullPath(filename, false);
    }

    private static String doGetFullPath(String filename, boolean includeSeparator) {
        if(filename == null) {
            return null;
        } else {
            int prefix = getPrefixLength(filename);
            if(prefix < 0) {
                return null;
            } else if(prefix >= filename.length()) {
                return includeSeparator?getPrefix(filename):filename;
            } else {
                int index = indexOfLastSeparator(filename);
                if(index < 0) {
                    return filename.substring(0, prefix);
                } else {
                    int end = index + (includeSeparator?1:0);
                    if(end == 0) {
                        ++end;
                    }

                    return filename.substring(0, end);
                }
            }
        }
    }

    public static String getName(String filename) {
        if(filename == null) {
            return null;
        } else {
            int index = indexOfLastSeparator(filename);
            return filename.substring(index + 1);
        }
    }

    public static String getExtension(String filename) {
        if(filename == null) {
            return null;
        } else {
            int index = indexOfExtension(filename);
            return index == -1?"":filename.substring(index + 1);
        }
    }

    public static boolean isExtension(String filename, String extension) {
        if(filename == null) {
            return false;
        } else if(extension != null && extension.length() != 0) {
            String fileExt = getExtension(filename);
            return fileExt.equals(extension);
        } else {
            return indexOfExtension(filename) == -1;
        }
    }

    public static boolean isExtension(String filename, String[] extensions) {
        if(filename == null) {
            return false;
        } else if(extensions != null && extensions.length != 0) {
            String fileExt = getExtension(filename);
            String[] arr$ = extensions;
            int len$ = extensions.length;

            for(int i$ = 0; i$ < len$; ++i$) {
                String extension = arr$[i$];
                if(fileExt.equals(extension)) {
                    return true;
                }
            }

            return false;
        } else {
            return indexOfExtension(filename) == -1;
        }
    }

    public static boolean isExtension(String filename, Collection<String> extensions) {
        if(filename == null) {
            return false;
        } else if(extensions != null && !extensions.isEmpty()) {
            String fileExt = getExtension(filename);
            Iterator i$ = extensions.iterator();

            String extension;
            do {
                if(!i$.hasNext()) {
                    return false;
                }

                extension = (String)i$.next();
            } while(!fileExt.equals(extension));

            return true;
        } else {
            return indexOfExtension(filename) == -1;
        }
    }

    static String[] splitOnTokens(String text) {
        if(text.indexOf(63) == -1 && text.indexOf(42) == -1) {
            return new String[]{text};
        } else {
            char[] array = text.toCharArray();
            ArrayList list = new ArrayList();
            StringBuilder buffer = new StringBuilder();

            for(int i = 0; i < array.length; ++i) {
                if(array[i] != 63 && array[i] != 42) {
                    buffer.append(array[i]);
                } else {
                    if(buffer.length() != 0) {
                        list.add(buffer.toString());
                        buffer.setLength(0);
                    }

                    if(array[i] == 63) {
                        list.add("?");
                    } else if(list.isEmpty() || i > 0 && !((String)list.get(list.size() - 1)).equals("*")) {
                        list.add("*");
                    }
                }
            }

            if(buffer.length() != 0) {
                list.add(buffer.toString());
            }

            return (String[])list.toArray(new String[list.size()]);
        }
    }

    static {
        SYSTEM_SEPARATOR = File.separatorChar;
        if(isSystemWindows()) {
            OTHER_SEPARATOR = 47;
        } else {
            OTHER_SEPARATOR = 92;
        }

    }
}

