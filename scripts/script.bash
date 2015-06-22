## Prefix of the log filenames to be concatenated.
FILE_PREFIX=$1
OUTPUT_FILENAME=$2
CLASS_PATH="./bin"

echo "File Prefix "$1
echo "Output filename "$2

rm $OUTPUT_FILENAME"_TMP"
for f in echo ls "$FILE_PREFIX"*
do
	echo "Processing "$f
	sed 1,2d "$f" | awk -F '	' 'BEGIN {OFS="	"} { if ($4 != "\"Laboratório e.Learning\"")  {print} }' >> $OUTPUT_FILENAME"_TMP"
done

java -cp $CLASS_PATH log.parsing.Anonymizer $OUTPUT_FILENAME"_TMP" > $OUTPUT_FILENAME"_UNORDERED" 2> error.log
rm $OUTPUT_FILENAME"_TMP"

java -cp $CLASS_PATH log.readers.LogByTime $OUTPUT_FILENAME"_UNORDERED" "log.formats.MoodleOperation" > $OUTPUT_FILENAME 2>> error.log
rm $OUTPUT_FILENAME"_UNORDERED"

error_fs=$(wc -c <"error.log")
if [ $error_fs -ge 1 ]; then
	echo "ERRORS"
	cat error.log
else
	rm error.log
	echo no errors
fi


##EXAMPLE
#./scripts/script.bash Logs/XPTO/logs-2015 outputfile

